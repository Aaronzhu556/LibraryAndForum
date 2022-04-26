package org.com.Controller;

import org.com.Entity.Reply;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.ReplyService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
}
