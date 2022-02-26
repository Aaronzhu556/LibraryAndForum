package org.com.Controller;

import org.com.Entity.User;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping("/login")
    public MyResponse Login(@RequestBody User user){
        String code = userService.UserLogin(user.getUser_name(), user.getUser_password());
        if (code=="200") return new MyResponse(code,"登陆成功");
        else if (code=="201") return new MyResponse(code,"密码错误");
        else if (code=="202") return new MyResponse(code,"用户被冻结");
        else if (code=="203") return new MyResponse(code,"无此用户");
        else return new MyResponse(code,"发生错误");
    }
}
