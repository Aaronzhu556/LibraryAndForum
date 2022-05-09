package org.com.Controller;
import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.Entity.User;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.UserService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public MyResponse Login(@RequestBody User user, HttpServletResponse response){
        String code = userService.UserLogin(user.getUser_name(), user.getUser_password());
        if (code=="200") {
            String token = JwtUtil.CreateToken(user.getUser_name());
            response.setHeader("token",token); //JWT验证
            return new MyResponse(code,"登陆成功",user.getUser_name(),null,"");
        }

        else if (code=="201") return new MyResponse(code,"密码错误","",null,"");
        else if (code=="202") return new MyResponse(code,"用户被冻结","",null,"");
        else if (code=="203") return new MyResponse(code,"无此用户","",null,"");
        else return new MyResponse(code,"发生错误","",null,"");
    }

    @ResponseBody
    @RequestMapping("/register")
    public MyResponse Register(@RequestBody User user){
        String code = userService.UserRegister(user);
        if (code.equals("200")) return new MyResponse(code,"注册成功","",null,"");
        else return new MyResponse(code,"注册失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/uploadimg")
    public MyResponse UploadUserImg(@RequestParam("user_img") MultipartFile multipartFile, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            String s = userService.UploadUserImg(multipartFile, JwtUtil.GetInformation(token));
            if (s != null) return new MyResponse("200","上传头像成功",s,null,"");
            else return new MyResponse("201","上传头像失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/queryuserimg")
    public MyResponse QueryUserImg(@RequestParam String user_name,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            String userImg = userService.QueryUserImg(user_name);
            return new MyResponse("200","查询成功",userImg,null,"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getallusername")
    public MyResponse GetAllUserName(){
        List<String> allUserName = userService.GetAllUserName();
        return new MyResponse("200","查询成功","",allUserName,"");
    }

    @ResponseBody
    @RequestMapping("/getalluser")
    public MyResponse GetAllUser(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<User> users = userService.GetAllUser(queryInfo);
            if (!users.isEmpty()) return new MyResponse("200","查询成功",String.valueOf(users.size()),users,"");
            else return new MyResponse("201","暂无用户数据","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/changestatus")
    public MyResponse ChangeStatus(@RequestParam int user_id,@RequestParam String user_status,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = userService.ChangeUserStatus(user_id,user_status);
            if (i!=0) return new MyResponse("200","修改用户状态成功","",null,"");
            else return new MyResponse("201","修改用户状态失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/getallusernum")
    public MyResponse GetAllUserNum(@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = userService.GetAllUserNum();
            return new MyResponse("200","查询成功",String.valueOf(i),null,"");

        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/gethotuser")
    public MyResponse GetHotUser(@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<User> userList = userService.GetHotUser();
            return new MyResponse("200","获取成功","",userList.subList(0,4),"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/queryuserinfo")
    public MyResponse GetUserInfo(@RequestParam String user_name,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            User user = userService.GetUserInfo(user_name);
            return new MyResponse("200","查询成功","",user,"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/updateuserinfo")
    public MyResponse EditUserInfo(@RequestBody User user,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            userService.EditUserInfo(user);
            return new MyResponse("200","更新成功","",null,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }
}
