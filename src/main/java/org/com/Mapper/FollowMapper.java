package org.com.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface FollowMapper {

    //查询关注者
    @Select("select follow_user_to from t_follow where follow_user_from=#{follow_user_from}")
    public List<String> GetUserFollowing(String c);
    //查询粉丝
    @Select("select follow_user_from from t_follow where follow_user_to=#{follow_user_to}")
    public List<String> GetUserFans(String follow_user_to);
    //插入关注记录
    @Insert("insert into t_follow(follow_user_from,follow_user_to) values(#{follow_user_from},#{follow_user_to})")
    public int FollowUser(String follow_user_from,String follow_user_to);

    @Delete("delete from t_follow where follow_user_from=#{follow_user_from} and follow_user_to=#{follow_user_to}")
    public int UnFollowUser(String follow_user_from,String follow_user_to);
}
