package org.com.Controller;


import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.Entity.User;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.BookService;
import org.com.Service.Interface.BorrowService;
import org.com.Service.Interface.UserService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookService bookService;
    private BorrowService borrowService;


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

            List<Book> books = bookService.QueryBook(queryInfo.getQuerytext());
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
                            System.out.println("caocao coaocoao");
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
    @RequestMapping("/borrowbooks")//这里没写完   借书完了应该要加一条借阅记录
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
}
