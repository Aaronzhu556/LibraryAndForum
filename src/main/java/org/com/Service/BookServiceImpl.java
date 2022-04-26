package org.com.Service;

import org.com.Entity.Book;
import org.com.Entity.Borrow;
import org.com.Entity.QueryInfo;
import org.com.Entity.User;
import org.com.Mapper.BookMapper;
import org.com.Mapper.BorrowMapper;
import org.com.Mapper.CategoryMapper;
import org.com.Mapper.UserMapper;
import org.com.Redis.RedisServer;
import org.com.Service.Interface.BookService;
import org.com.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisServer redisServer;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BorrowMapper borrowMapper;

    @Value("D:/graduateproject2photo/")
    private String imgPath;

    @Override
    public List<Book> QueryBook(QueryInfo queryInfo){
        int category_id;
        if (queryInfo.getQuerytext().equals("")){
            List<Book> bookList = bookMapper.QueryBookAll();
            for (Book book: bookList){
                List<String> Namelist = new LinkedList<>();
                category_id = book.getBook_category();
                int parent;
                do {
                    Namelist.add(categoryMapper.QueryNameByID(category_id));
                    parent=categoryMapper.QueryParentById(category_id);
                    category_id = parent;
                }while(parent!=0);
                book.setBook_category_name(Namelist.toString());

                List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
                int temp=0;
                double star = 0,star_temp=0;
                for (int num:list) temp=temp+num;
                if (temp>0) {
                    star = (float) temp / list.size();
                    BigDecimal bigDecimal = new BigDecimal(star);
                    star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
                }
                book.setBook_star(star_temp);


            }
            return bookList;
        }else if (queryInfo.getQuerytext().equals("根据类型排序")&&queryInfo.getQuerycateid()!=0){
            List<Book> books = bookMapper.GetAllBookByCategory(queryInfo.getQuerycateid());
            System.out.println("这是根据类型排序");
            for (Book book: books){
                List<String> Namelist = new LinkedList<>();
                category_id = book.getBook_category();
                int parent;
                do {
                    Namelist.add(categoryMapper.QueryNameByID(category_id));
                    parent=categoryMapper.QueryParentById(category_id);
                    category_id = parent;
                }while(parent!=0);
                book.setBook_category_name(Namelist.toString());
                List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
                int temp=0;
                double star = 0,star_temp=0;
                for (int num:list) temp=temp+num;
                if (temp>0) {
                    star = (double) temp / list.size();
                    BigDecimal bigDecimal = new BigDecimal(star);
                    star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                book.setBook_star(star_temp);



            }
            return books;
        }







        else {
            List<Book> bookList = bookMapper.QueryBookByName(queryInfo.getQuerytext());
            for (Book book: bookList){
                List<String> Namelist = new LinkedList<>();
                category_id = book.getBook_category();
                int parent;
                do {
                    Namelist.add(categoryMapper.QueryNameByID(category_id));
                    parent=categoryMapper.QueryParentById(category_id);
                    category_id = parent;
                }while(parent!=0);
                book.setBook_category_name(Namelist.toString());
                List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
                int temp=0;
                double star = 0,star_temp=0;
                for (int num:list) temp=temp+num;
                if (temp>0) {
                    star = (double) temp / list.size();
                    BigDecimal bigDecimal = new BigDecimal(star);
                    star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                book.setBook_star(star_temp);



            }
            return bookList;
        }





    }

    @Override
    public String BorrowBook(int book_id,String user_name){

        User user = userMapper.QuerybyName(user_name);
        System.out.println(user.toString());
        if (Integer.parseInt(user.getUser_book_num())<5){
            redisServer.putValue(String.valueOf(book_id),user_name);
            System.out.println(redisServer.getValue(String.valueOf(book_id)));
            bookMapper.UpdateBookStatus(book_id,"2");//2代表被预约
            /*
            int book_num = Integer.parseInt(user.getUser_book_num());
            book_num = book_num +1;
            userMapper.UpdateUserBookNum(String.valueOf(book_num),user_name);
            */

            return "200";
        }else return "201";

    }
    @Override
    public String DeleteBook(int book_id){
        int i = bookMapper.DeleteBook(book_id);
        if (i!=0) return "200";
        else return "201";

    }
    @Override
    public List<Book> GetAllBookByUser(String  user_name){
        List<Borrow> BookIdList = borrowMapper.GetAllBookIdByUser(user_name);
        List<Book> BookList = new LinkedList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        for (Borrow borrow:BookIdList){
            Book book = bookMapper.GetAllBookByUser(borrow.getBorrow_book_id());
            book.setBook_borrow_end_time(borrow.getBorrow_end_time());
            ParsePosition position= new ParsePosition(0);
            Date date_end = formatter.parse(borrow.getBorrow_end_time(),position);
            Date date_now = new Date();
            int res = date_now.compareTo(date_end);
            if(res<0) {
                book.setBook_is_expired(false);

            }else {
                book.setBook_is_expired(true);

            }
            BookList.add(book);
        }


        return BookList;
    }

    @Override
    public int ReturnBook(String user_name,int book_id){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        int i = borrowMapper.ChangeBorrowInfo(formatter.format(date), user_name, book_id);
        int flag=0;
        if (i!=0){
            flag = bookMapper.UpdateBookStatus(book_id,"1");
            int num = Integer.parseInt(userMapper.GetBookNumByUser(user_name));
            num = num - 1;
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+num);
            userMapper.UpdateUserBookNum(String.valueOf(num),user_name);
        }
        return flag;


    }
    @Override
    public String UploadBookImg(MultipartFile multipartFile){
       return ImageUtil.UploadPhoto(multipartFile, imgPath, "");
    }
    @Override
    public int AddBook(Book book){
        return bookMapper.AddBook(book);
    }

    @Override
    public List<Book>GetAllBookWithoutComment(String user_name){
        List<Integer> list = borrowMapper.QueryNoCommentBorrow(user_name);
        List<Book> bookList = new LinkedList<>();
        for (int book_id:list){

            bookList.add(bookMapper.QueryBookById(book_id));
        }
        return bookList;
    }

    @Override
    public List<Book>GetNewBook(){
        List<Book> bookList = QueryBook(new QueryInfo("",1,10000,""));
        List<Book> books = new LinkedList<>();
//        int i= bookList.size()-3;//这里可以灵活修改
//        for (;i<bookList.size();i++){
//            books.add(bookList.get(i));
//        }
        return bookList;
    }

    @Override
    public Book GetBookDetails(int book_id){
        Book book = bookMapper.QueryBookById(book_id);
        List<String> Namelist = new LinkedList<>();
        int category_id = book.getBook_category();
        int parent;
        do {
            Namelist.add(categoryMapper.QueryNameByID(category_id));
            parent=categoryMapper.QueryParentById(category_id);
            category_id = parent;
        }while(parent!=0);
        book.setBook_category_name(Namelist.toString());

        List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
        int temp=0;
        double star = 0,star_temp=0;
        for (int num:list) temp=temp+num;
        if (temp>0) {
            star = (float) temp / list.size();
            BigDecimal bigDecimal = new BigDecimal(star);
            star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_DOWN).doubleValue();
        }
        book.setBook_star(star_temp);
        return book;
    }
    @Override
    public List<Book>GetAllBookByCategory(int category_id){
        return bookMapper.GetAllBookByCategory(category_id);
    }

    @Override
    public List<Book>GetAllBookByBorrow(){
        List<Book> books = bookMapper.QueryBookAll();
        for (Book book:books){
            List<Integer> list1 = borrowMapper.GetBorrowIdByBook(book.getBook_id());
            book.setBook_borrow_num(list1.size());
            List<String> Namelist = new LinkedList<>();
            int category_id = book.getBook_category();
            int parent;
            do {
                Namelist.add(categoryMapper.QueryNameByID(category_id));
                parent=categoryMapper.QueryParentById(category_id);
                category_id = parent;
            }while(parent!=0);
            book.setBook_category_name(Namelist.toString());
            List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
            int temp=0;
            double star = 0,star_temp=0;
            for (int num:list) temp=temp+num;
            if (temp>0) {
                star = (double) temp / list.size();
                BigDecimal bigDecimal = new BigDecimal(star);
                star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            book.setBook_star(star_temp);
        }
        books.sort(Comparator.comparing(Book::getBook_borrow_num).reversed()); //根据借阅量降序
        return books;
    }
    @Override
    public List<Book>GetAllBookByStar(){
        List<Book> bookList = bookMapper.QueryBookAll();
        for (Book book:bookList){
            List<Integer> list1 = borrowMapper.GetBorrowIdByBook(book.getBook_id());
            book.setBook_borrow_num(list1.size());
            List<String> Namelist = new LinkedList<>();
            int category_id = book.getBook_category();
            int parent;
            do {
                Namelist.add(categoryMapper.QueryNameByID(category_id));
                parent=categoryMapper.QueryParentById(category_id);
                category_id = parent;
            }while(parent!=0);
            book.setBook_category_name(Namelist.toString());
            List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
            int temp=0;
            double star = 0,star_temp=0;
            for (int num:list) temp=temp+num;
            if (temp>0) {
                star = (double) temp / list.size();
                BigDecimal bigDecimal = new BigDecimal(star);
                star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            book.setBook_star(star_temp);
        }


        bookList.sort(Comparator.comparing(Book::getBook_star).reversed());
        return bookList;
    }
    @Override
    public List<Book>GetAllBookByTime(){
        List<Book> bookList = bookMapper.QueryBookAll();
        for (Book book:bookList){
            List<Integer> list1 = borrowMapper.GetBorrowIdByBook(book.getBook_id());
            book.setBook_borrow_num(list1.size());
            List<String> Namelist = new LinkedList<>();
            int category_id = book.getBook_category();
            int parent;
            do {
                Namelist.add(categoryMapper.QueryNameByID(category_id));
                parent=categoryMapper.QueryParentById(category_id);
                category_id = parent;
            }while(parent!=0);
            book.setBook_category_name(Namelist.toString());
            List<Integer> list = borrowMapper.QueryStarById(book.getBook_id());
            int temp=0;
            double star = 0,star_temp=0;
            for (int num:list) temp=temp+num;
            if (temp>0) {
                star = (double) temp / list.size();
                BigDecimal bigDecimal = new BigDecimal(star);
                star_temp = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            }
            book.setBook_star(star_temp);
        }
        bookList.sort(Comparator.comparing(Book::getBook_id).reversed());
        return bookList;
    }

    @Override
    public int GetAllBookNum(){
        return bookMapper.QueryBookAll().size();
    }
}
