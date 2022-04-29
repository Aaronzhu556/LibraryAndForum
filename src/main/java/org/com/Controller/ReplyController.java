package org.com.Controller;

import org.com.Entity.Article;
import org.com.Entity.Reply;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.ReplyService;
import org.com.util.JwtUtil;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reply")
public class ReplyController {


    @Autowired
    private ReplyService replyService;

    @RequestMapping("/addnewreply")
    @ResponseBody
    public MyResponse AddNewReply(@RequestBody Reply reply, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = replyService.AddReply(reply);
            if (i!=0) return new MyResponse("200","回复成功","",null,"");
            else return new MyResponse("201","回复失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @RequestMapping("/deletereply")
    @ResponseBody
    public MyResponse DeleteReply(@RequestParam int reply_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = replyService.DeleteReply(reply_id);
            if (i!=0) return new MyResponse("200","删除成功","",null,"");
            else return new MyResponse("201","删除失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");

    }

    @RequestMapping("/getunreadreply")
    @ResponseBody
    public MyResponse GetAllUnReadReply(@RequestParam String user_name,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Reply> replies = replyService.GetAllUnReadReply(user_name);
            return new MyResponse("200","获取成功","",replies,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @RequestMapping("/readreply")
    @ResponseBody
    public MyResponse ReadReply(@RequestParam int reply_id,@RequestParam int reply_comment_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            Article article = replyService.ReadReply(reply_id,reply_comment_id);
            return new MyResponse("200","读取回复成功","",article,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/readall")
    public MyResponse ReadAllReply(@RequestBody List<Integer> replyIdList,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)) {
            replyService.ReadAllReply(replyIdList);
            return new MyResponse("200","已读全部成功","",null,"");
        } else return new MyResponse("201","Jwt验证失败","",null,"");
    }
}
