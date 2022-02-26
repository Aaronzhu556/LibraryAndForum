package org.com.Service.Interface;

import org.springframework.stereotype.Service;
import org.com.Entity.*;
@Service
public interface UserService {
    public String UserLogin(String user_name, String user_password);
    //public String UserRegister(User user);
}
