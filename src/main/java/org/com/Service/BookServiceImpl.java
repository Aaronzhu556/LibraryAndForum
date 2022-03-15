package org.com.Service;

import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.Entity.User;
import org.com.Mapper.BookMapper;
import org.com.Mapper.CategoryMapper;
import org.com.Mapper.UserMapper;
import org.com.Redis.RedisServer;
import org.com.Service.Interface.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public List<Book> QueryBook(String book_name){
        int category_id;
        if (book_name.equals("")){
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

            }
            return bookList;
        }else {
            List<Book> bookList = bookMapper.QueryBookByName(book_name);
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
//
//            bookMapper.UpdateBookStatus(book_id);
//            int book_num = Integer.parseInt(user.getUser_book_num());
//            book_num ++;
//            userMapper.UpdateUserBookNum(String.valueOf(book_num),user_name);
            return "200";
        }else return "201";

    }
    @Override
    public String DeleteBook(int book_id){
        int i = bookMapper.DeleteBook(book_id);
        if (i!=0) return "200";
        else return "201";

    }

}
