package org.com.Controller;

import org.com.Entity.Book;
import org.com.Entity.Borrow;
import org.com.Mapper.BookMapper;
import org.com.MyResponse.MyResponse;
import org.com.Redis.RedisServer;
import org.com.Service.Interface.BookService;
import org.com.Service.Interface.BorrowService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private RedisServer redisServer;
    @Autowired
    private BookService bookService;

    @ResponseBody
    @RequestMapping("/borrowbook")
    public MyResponse AddBorrowBookInfo(@RequestBody Borrow borrow, @RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            String s = borrowService.AddBookBorrowInfo(borrow.getBorrow_user_name(), borrow.getBorrow_book_id(), borrow.getBorrow_user_temperature());
            if(s=="200"){
                Boolean aBoolean = redisServer.deleteKey(String.valueOf(borrow.getBorrow_book_id()));
                return new MyResponse(s,"用户"+borrow.getBorrow_user_name()+"借书成功","",null,"");
            }
            else return new MyResponse(s,"用户已经借了5本书,无法再借","",null,"");

        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/addcomment")
    public MyResponse AddBookComment(@RequestParam String borrow_comment_context,@RequestParam int borrow_comment_star,@RequestParam int borrow_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = borrowService.UpdateBookCommentInfo(borrow_comment_context, borrow_comment_star,borrow_id);
            if (i!=0) return new MyResponse("200","评论成功","",null,"");
            else return new MyResponse("201","评论失败","",null,"");

        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/queryborrowwithoutcomment")
    public MyResponse QueryAllBorrowWithoutComment(@RequestParam String user_name,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Borrow> borrows = borrowService.QueryAllBorrowWithoutComment(user_name);
            if (!borrows.isEmpty())return new MyResponse("200","查询成功","",borrows,"");
            else return new MyResponse("201","暂无信息","",null,"");

        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/querybookcommentbyid")
    public MyResponse QueryBookComment(@RequestParam int book_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Borrow> borrows = borrowService.QueryAllCommentById(book_id);
            if (!borrows.isEmpty()) return new MyResponse("200","查询成功","",borrows,"");
            else return new MyResponse("201","查询失败","",borrows,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getallborrow")
    public MyResponse GetAllBorrow(@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Book> bookList = bookService.GetAllBookByBorrow();
            return new MyResponse("200","查询成功","",bookList.subList(0,5),"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }

}
