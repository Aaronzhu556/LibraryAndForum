package org.com.Mapper;

import org.apache.ibatis.annotations.*;
import org.com.Entity.Article;

import java.util.List;

@Mapper
public interface ArticleMapper {

    @Select("select * from t_article")
    public List<Article> QueryAllArticle();

    @Select("select * from t_article where article_id=#{article_id}")
    public Article QueryArticleById(int article_id);

    @Select("select * from t_article where article_title like '%' #{article_title} '%'")//SQL like模糊查询
    public List<Article> QueryArticleByTitle(String article_title);

    @Select("select article_content from t_article where article_id=#{article_id}")
    public String GetContentByID(int article_id);

    @Insert("insert into t_article(article_title,article_content,article_user_name,article_time) values(#{article_title},#{article_content},#{article_user_name},#{article_time})")
    public int AddArticle(Article article);

    @Delete("delete from t_article where article_id=#{article_id}")
    public int DeleteArticle(int article_id);

    @Insert("insert into t_like(like_article_id,like_user_name) values(#{like_article_id},#{like_user_name})")
    public int AddArticleLike(int like_article_id,String like_user_name);

    @Delete("delete from t_like where like_article_id=#{like_article_id} and like_user_name=#{like_user_name}")
    public int DeleteArticleLike(int like_article_id,String like_user_name);

    @Select("select article_star from t_article where article_id=#{article_id}")
    public int GetArticleStar(int article_id);

    @Update("update t_article set article_star=#{article_star} where article_id=#{article_id}")
    public int UpdateArticleStar(int article_star,int article_id);

    @Select("select article_reply_num from t_article where article_id=#{article_id}")
    public int GetArticleReplyNum(int article_id);

    @Update("update t_article set article_reply_num=#{article_reply_num} where article_id=#{article_id}")
    public int UpdateArticleReplyNum(int article_reply_num,int article_id);

    @Select("select * from t_article where article_user_name=#{article_user_name}")
    public List<Article> GetAllArticleByUser(String article_user_name);

    @Select("select article_click from t_article where article_id=#{article_id}")
    public int GetArticleClick(int article_id);

    @Update("update t_article set article_click=#{article_click} where article_id=#{article_id}")
    public int UpdateArticleClick(int article_click,int article_id);

    @Select("select article_id from t_article")
    public List<Integer>GetAllArticleId();


}
