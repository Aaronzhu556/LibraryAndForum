package org.com.Service.Interface;

import org.springframework.stereotype.Service;
import org.com.Entity.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    public String UserLogin(String user_name, String user_password);
    public String UserRegister(User user);
    public String UploadUserImg(MultipartFile multipartFile,String user_name);
    public String QueryUserImg(String user_name);
    public List<String> GetAllUserName();
    public List<User> GetAllUser(QueryInfo queryInfo);
    public int ChangeUserStatus(int user_id,String user_status);
    public int GetAllUserNum();



}
