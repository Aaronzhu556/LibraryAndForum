package org.com.Controller;

import org.com.Entity.Borrow;
import org.com.Mapper.BookMapper;
import org.com.MyResponse.MyResponse;
import org.com.Redis.RedisServer;
import org.com.Service.Interface.BorrowService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;
    @Autowired
    private RedisServer redisServer;

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

}
