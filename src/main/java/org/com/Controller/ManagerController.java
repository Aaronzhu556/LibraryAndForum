package org.com.Controller;

import org.com.Entity.Manager;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.ManagerService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/manager")
public class ManagerController {

    @Autowired
    private ManagerService managerService;

    @ResponseBody
    @RequestMapping("/login")
    public MyResponse managerLogin(@RequestBody Manager manager, HttpServletResponse response){
        String code = managerService.managerLogin(manager.getManager_name(), manager.getManager_password());
        if (code=="200") {
            response.setHeader("token", JwtUtil.CreateToken(manager.getManager_name()));
            return new MyResponse(code, "登录成功","",null,"");
        }
        else if (code=="201") return new MyResponse(code,"密码错误,请检查后再登录","",null,"");
        else if (code=="202") return new MyResponse(code,"无此管理员","",null,"");
        else return new MyResponse(code,"登录错误","",null,"");

    }
}
