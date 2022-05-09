package org.com.Service;

import org.com.Entity.Article;
import org.com.Entity.QueryInfo;
import org.com.Entity.User;
import org.com.Mapper.ArticleMapper;
import org.com.Mapper.FollowMapper;
import org.com.Mapper.UserMapper;
import org.com.Service.Interface.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.com.util.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Value("D:/graduateproject2photo/")
    private String imgPath;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private FollowMapper followMapper;

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

    @Override
    public String UserRegister(User user){
        user.setUser_password(PasswordUtil.getPassword(user.getUser_password()));
        int register = userMapper.register(user);
        if (register!=0) return "200";
        else return "201";//注册失败
    }
    @Override
    public String UploadUserImg(MultipartFile multipartFile,String user_name){
        String user_img= ImageUtil.UploadPhoto(multipartFile,imgPath,user_name);
        userMapper.UploadUserImg(user_name,user_img);
        return user_img;
    }
    @Override
    public String QueryUserImg(String user_name){
        return userMapper.GetUserImgByName(user_name);
    }


    @Override
    public List<String> GetAllUserName(){
        return userMapper.GetAllUserName();
    }

    @Override
    public List<User> GetAllUser(QueryInfo queryInfo){
        List<User> userList = new LinkedList<>();
        if (!queryInfo.getQuerytext().equals("")){
             userList = userMapper.GetUserByName(queryInfo.getQuerytext());
        }else {
             userList = userMapper.GetAllUser();
        }
        for (User user:userList) user.setUser_img(user.getUser_img());
        return userList;
    }

    @Override
    public int ChangeUserStatus(int user_id,String user_status){
        return userMapper.UpdateUserStatus(user_status,user_id);
    }
    @Override
    public int GetAllUserNum(){
        return userMapper.GetAllUser().size();
    }
    @Override
    public List<User>GetHotUser(){
        List<User> userList = userMapper.GetAllUser();
        List<Article> articles = articleMapper.QueryAllArticle();
        List<Article> temp = articles.stream().collect(Collectors.toList());
        for (User user:userList){
            int user_article_num = articleMapper.GetAllArticleByUser(user.getUser_name()).size();
            int user_fans = followMapper.GetUserFans(user.getUser_name()).size();
            int user_like=0;
            for (int i=0;i<temp.size();i++){
                if (temp.get(i).getArticle_user_name()==user.getUser_name()) user_like = user_like +1;
            }
            user.setUser_hot(user_article_num+user_fans+user_like);
            user.setUser_img(user.getUser_img());
            user.setUser_fans(user_fans);
            user.setUser_great(user_like);
            user.setUser_article_num(user_article_num);
        }
        userList.sort(Comparator.comparing(User::getUser_hot).reversed());
        return userList;
    }
    @Override
    public User GetUserInfo(String user_name){
        return userMapper.QuerybyName(user_name);
    }
    @Override
    public int EditUserInfo(User user){
        return userMapper.UpdateUserInfo(user);
    }
}
