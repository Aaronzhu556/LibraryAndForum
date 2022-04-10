package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.com.Entity.Article;
import org.com.Entity.Comment;

import java.util.List;

@Mapper
public interface CommentMapper {
    @Select("select * from t_comment where comment_article_id=#{comment_article_id}")
    public List<Comment> QueryAllCommentByArticleId(int comment_article_id);
    @Insert("insert into t_comment(comment_user_name,comment_time,comment_content,comment_article_id) values(#{comment_user_name},#{comment_time},#{comment_content},#{comment_article_id})")
    public int InsertComment(Comment comment);

}
