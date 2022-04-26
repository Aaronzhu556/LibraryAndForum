package org.com.Controller;

import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.FollowService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    @RequestMapping("/getfollowing")
    @ResponseBody
    public MyResponse GetFollowing(@RequestParam String follow_user_from, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<String> list = followService.GetUserFollowing(follow_user_from);
            if (!list.isEmpty()) return new MyResponse("200","查询成功",String.valueOf(list.size()),list,"");
            else return new MyResponse("201","暂无关注","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @RequestMapping("/getfans")
    @ResponseBody
    public MyResponse GetFans(@RequestParam String follow_user_to, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<String> list = followService.GetUserFans(follow_user_to);
            if (!list.isEmpty()) return new MyResponse("200","查询成功",String.valueOf(list.size()),list,"");
            else return new MyResponse("201","暂无粉丝","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @RequestMapping("/followuser")
    @ResponseBody
    public MyResponse FollowUser(@RequestParam String follow_user_from,@RequestParam String follow_user_to, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = followService.FollowUser(follow_user_from, follow_user_to);
            if (i!=0) return new MyResponse("200","关注成功","",null,"");
            else return new MyResponse("201","关注失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @RequestMapping("/unfollowuser")
    @ResponseBody
    public MyResponse UnFollowUser(@RequestParam String follow_user_from,@RequestParam String follow_user_to,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = followService.UnFollowUser(follow_user_from, follow_user_to);
            if (i!=0) return new MyResponse("200","取消关注成功","",null,"");
            else  return new MyResponse("201","取消关注失败","",null,"");
        } return new MyResponse("202","Jwt验证失败","",null,"");
    }


}
