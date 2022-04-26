package org.com.Controller;

import org.com.Entity.Comment;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.CommentService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @RequestMapping("/queryallcomment")
    @ResponseBody
    public MyResponse QueryAllComment(@RequestParam int comment_article_id,@RequestParam int pagesize,@RequestParam int pagenum, @RequestHeader("Authorization") String token) {
        if (JwtUtil.VerifyToken(token)) {
            List<Comment> comments = commentService.QueryAllComment(comment_article_id);
            List<Comment> commentList = new LinkedList<>();
            if (!comments.isEmpty()) {
                int count = 0;
                for (int i = (pagenum-1)*pagesize; count < pagesize && i < comments.size(); i++) {
                    commentList.add(comments.get(i));
                    count++;
                }
                return new MyResponse("200", "加载成功", String.valueOf(comments.size()), commentList, "");
            }
            return new MyResponse("201", "此帖子现在没有任何回复", "", null, "");

        }
        return new MyResponse("202", "Jwt验证失败", "", null, "");
    }


    @RequestMapping("/addnewcomment")
    @ResponseBody
    public MyResponse AddNewComment(@RequestBody Comment comment,@RequestHeader("Authorization") String token){
        int flag=0;
        if (JwtUtil.VerifyToken(token)){
            flag = commentService.AddNewComment(comment);

            if (flag!=0) return new MyResponse("200","回复成功","",null,"");
            else return new MyResponse("201","添加回复失败,请重试","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }


    @RequestMapping("/deletecomment")
    @ResponseBody
    public MyResponse DeleteComment(@RequestParam int comment_id,@RequestHeader("Authorization")String token ){
        if (JwtUtil.VerifyToken(token)){
            int i = commentService.DeleteComment(comment_id);
            if (i!=0) return new MyResponse("200","删除成功","",null,"");
            else return new MyResponse("201","删除失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
}