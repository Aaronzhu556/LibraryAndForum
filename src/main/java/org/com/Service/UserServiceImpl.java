package org.com.Service;

import org.com.Entity.User;
import org.com.Mapper.UserMapper;
import org.com.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.com.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public String UserLogin(String user_name, String user_password){
        String code="";
        User user = userMapper.QuerybyName(user_name);
        if (user!=null){
            if (user.getUser_password().equals(PasswordUtil.getPassword(user_password))){
                if (user.getUser_status().equals("1")) {
                    code="200";//成功登陆
                }else{
                    code="202";//用户被冻结
                }

            }else {
                code="201";//密码错误
            }

        }else {
            code="203";//不存在该用户
        }
        return code;

    }
}
