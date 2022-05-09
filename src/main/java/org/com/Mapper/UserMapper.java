package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.com.Entity.User;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select * from t_user where user_name=#{user_name}")
    public User QuerybyName(String user_name);

    @Insert("insert into t_user(user_name,user_password,user_phone) values(#{user_name},#{user_password},#{user_phone})")
    public int register(User user);

    @Update("update t_user set user_book_num=#{user_book_num} where user_name=#{user_name}")
    public int UpdateUserBookNum(String user_book_num,String  user_name);

    @Select("select user_book_num  from t_user where user_name=#{user_name}")
    public String GetBookNumByUser(String user_name);

    @Select("select user_img from t_user where user_name=#{user_name}")
    public String GetUserImgByName(String user_name);

    @Update("update t_user set user_img=#{user_img} where user_name=#{user_name}")
    public int UploadUserImg(String user_name , String user_img);

    @Select("select user_name from t_user")
    public List<String> GetAllUserName();

    @Select("select * from t_user")
    public List<User> GetAllUser();
    @Select("select * from t_user where user_name=#{user_name}")
    public List<User> GetUserByName(String user_name);

    @Update("update t_user set user_status=#{user_status} where user_id=#{user_id}")
    public int UpdateUserStatus(String user_status,int user_id);

    @Update("update t_user set user_phone=#{user_phone},user_privacy=#{user_privacy},user_motto=#{user_motto} where user_id=#{user_id}")
    public int UpdateUserInfo(User user);
}
