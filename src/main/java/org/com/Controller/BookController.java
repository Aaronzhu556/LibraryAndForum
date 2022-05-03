package org.com.Controller;


import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.Entity.User;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.BookService;
import org.com.Service.Interface.BorrowService;
import org.com.Service.Interface.UserService;
import org.com.util.JwtUtil;
import org.com.util.PythonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.plugin2.message.Message;

import javax.faces.annotation.RequestMap;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;



    /*
    @ResponseBody
    @RequestMapping("/querybooks")


    public MyResponse QueryBooksAll(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization") String  token){

        int count = 0;
        if (JwtUtil.VerifyToken(token)){//JWT验证
            List<Book> bookList = new LinkedList<>();
            List<Book> books = bookService.QueryBookAll();

            for (int i=((queryInfo.getPagenum()-1)*queryInfo.getPagesize());count<queryInfo.getPagesize()&&i<books.size();i++){
                bookList.add(books.get(i));
                count++;
            }
            return new MyResponse("200","查询成功",String.valueOf(books.size()),bookList);
        }else return new MyResponse("201","JWT验证失败","",null);

    }


     */

    @ResponseBody
    @RequestMapping("/querybooks")
    public MyResponse QueryBookAll(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization") String  token){
        if (JwtUtil.VerifyToken(token)){

            List<Book> books = bookService.QueryBook(queryInfo);
            List<Book> bookList = new LinkedList<>();
            int page = 0;
            if (!books.isEmpty()){

                int count = 0;
                if (!queryInfo.getQuerydata().equals(queryInfo.getQuerytext())&&queryInfo.getPagenum()!=1) { // 说明这是第一次查询
                    for (int i = 0; count < queryInfo.getPagesize(); i++) {
                        try {
                            bookList.add(books.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    page = 1;
                }else {
                    for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                        try {

                            bookList.add(books.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
                System.out.println(bookList.toString());
                return new MyResponse("200","查询成功",String.valueOf(books.size()),bookList,String.valueOf(page));
            }else return new MyResponse("201","没有这本书,请联系管理员~","",null,"");

        }else return new MyResponse("202","JWt验证失败","",null,"");

    }

    /**   
     * 预约借书  这个功能我还没确定用redis还是简单逻辑判断
     *
     * ok 用redis吧
     * */
    @ResponseBody
    @RequestMapping("/borrowbooks")//这里没写完   借书完了应该要加一条借阅记录---已解决
    public MyResponse borrowBooks(@RequestParam int book_id, @RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            String code = "";

            String user_name = JwtUtil.GetInformation(token);
            code = bookService.BorrowBook(book_id,user_name);
            //String s = borrowService.AddBookBorrowInfo(user_name, book_id);
            if (code == "200") return new MyResponse(code,"预约成功,请您尽快取书","",null,"");
            else return new MyResponse(code,"预约失败,您已经借了5本书了","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/deletebooks")
    public MyResponse deletedbooks(@RequestParam int book_id,  @RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            String code = bookService.DeleteBook(book_id);
            if (code=="200") return new MyResponse(code,"删除成功","",null,"");
            else return new MyResponse(code,"删除失败,发生错误","",null,"");
        }else return new MyResponse("202","JWt验证失败","",null,"");

    }

    @ResponseBody
    @RequestMapping("/getallbookbyuser")
    public MyResponse getAllBookByUser(@RequestParam String user_name,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            System.out.println("后端接收到了请求");
            List<Book> bookList = bookService.GetAllBookByUser(user_name);
            if (!bookList.isEmpty()) return new MyResponse("200","查询成功","",bookList,"");
            else return new MyResponse("201","暂无借阅书籍","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/returnbook")
    public MyResponse returnBook(@RequestParam int book_id,@RequestParam String user_name,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            int i = bookService.ReturnBook(user_name, book_id);
            if (i!=0) return new MyResponse("200","归还成功","",null,"");
            else return new MyResponse("201","归还失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");

    }

    @ResponseBody
    @RequestMapping("/uploadbookimg")
    public MyResponse UploadBookImg(@RequestParam("book_img")MultipartFile multipartFile,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            String bookImg = bookService.UploadBookImg(multipartFile);
            if (!bookImg.equals("")) return new MyResponse("200","上传成功",bookImg,null,"");
            else return new MyResponse("201","上传失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/addbook")
    public MyResponse AddBook(@RequestBody Book book,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = bookService.AddBook(book);
            if (i>0) return new MyResponse("200","图书添加成功","",null,"");
            else return new MyResponse("201","图书添加失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/querybookwithoutcomment")
    public MyResponse QueryBookWithoutComment(@RequestParam String user_name,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Book> bookList = bookService.GetAllBookWithoutComment(user_name);
            if (bookList.size()!=0)return new MyResponse("200","查询成功","",bookList,"");
            else return new MyResponse("201","暂时没有数据","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getnewbook")
    public MyResponse GetNewBook(@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Book> books = bookService.GetNewBook();
            PythonUtils.recommendBook();
            return new MyResponse("200","查询成功",String.valueOf(books.size()),books,"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getbookdetails")
    public MyResponse GetBookDetails(@RequestParam int book_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            Book book = bookService.GetBookDetails(book_id);
            return new MyResponse("200","查询成功","",book,"");


        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getbookbyborrow")
    public MyResponse GetBookByBorrow(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Book> books = bookService.GetAllBookByBorrow();
            List<Book> bookList = new LinkedList<>();
            int count=0;
            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                try {

                    bookList.add(books.get(i));
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            if (!bookList.isEmpty()) return new MyResponse("200","查询成功",String.valueOf(books.size()),bookList,"");
            else return new MyResponse("201","暂无数据","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/getbookbystar")
    public MyResponse GetBookByStar(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Book> books = bookService.GetAllBookByStar();
            List<Book> bookList = new LinkedList<>();
            int count=0;
            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                try {

                    bookList.add(books.get(i));
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            if (!bookList.isEmpty()) return new MyResponse("200","查询成功",String.valueOf(books.size()),bookList,"");
            else return new MyResponse("201","暂无数据", "",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getbookbytime")
    public MyResponse GetAllBookByTime(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Book> books = bookService.GetAllBookByTime();
            List<Book> bookList = new LinkedList<>();
            int count=0;
            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                try {

                    bookList.add(books.get(i));
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            if (!bookList.isEmpty()) return new MyResponse("200","查询成功",String.valueOf(books.size()),bookList,"");
            else return new MyResponse("201","暂无数据", "",null,"");
        }else return new MyResponse("202","Jwt验证失败", "",null,"");
    }

    @ResponseBody
    @RequestMapping("/getallbooknum")
    public MyResponse GetAllBookNum(@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = bookService.GetAllBookNum();
            return new MyResponse("200","验证失败",String.valueOf(i),null,"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }

}
