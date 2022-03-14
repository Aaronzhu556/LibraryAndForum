package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.com.Entity.User;

@Mapper
public interface UserMapper {
    @Select("select * from t_user where user_name=#{user_name}")
    public User QuerybyName(String user_name);

    @Insert("insert into t_user(user_name,user_password,user_phone) values(#{user_name},#{user_password},#{user_phone})")
    public int register(User user);

    @Update("update t_user set user_book_num=#{user_book_num} where user_name=#{user_name}")
    public int UpdateUserBookNum(String user_book_num,String  user_name);

}
