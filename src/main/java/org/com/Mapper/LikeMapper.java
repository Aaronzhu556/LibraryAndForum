package org.com.Mapper;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LikeMapper {
    @Select("select like_article_id from t_like where like_user_name=#{like_user_name}")
    public List<Integer> GetAllArticleLike(String like_user_name);
}
