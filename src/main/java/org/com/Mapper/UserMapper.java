package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.com.Entity.User;

@Mapper
public interface UserMapper {
    @Select("select * from t_user where user_name=#{user_name}")
    public User QuerybyName(String user_name);

    @Insert("insert into t_user(user_name,user_password,user_phone) values(#{user_name},#{user_password},#{user_phone})")
    public int register(User user);
}
