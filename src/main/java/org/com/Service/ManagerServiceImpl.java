package org.com.Service;

import org.com.Entity.Manager;
import org.com.Mapper.ManagerMapper;
import org.com.Service.Interface.ManagerService;
import org.com.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private ManagerMapper managerMapper;

    @Override
    public String managerLogin(String manager_name,String manager_password){
        Manager manager = managerMapper.QueryByName(manager_name);
        if (manager!=null){
            if (manager.getManager_password().equals(PasswordUtil.getPassword(manager_password))){
                return "200"; //登录成功
            }else return "201"; // 密码错误
        }else return "202";//无此管理员
    }
}
