package org.com.Controller;
import org.com.Entity.User;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.UserService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

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
}
