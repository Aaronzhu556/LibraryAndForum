package org.com.Service;

import org.com.Entity.Article;
import org.com.Entity.QueryInfo;
import org.com.Mapper.ArticleMapper;
import org.com.Mapper.CommentMapper;
import org.com.Mapper.LikeMapper;
import org.com.Service.Interface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;




    @Override
    public List<Article> getAllArticle(QueryInfo queryInfo){
       if (!queryInfo.getQuerytext().equals("")){
           return articleMapper.QueryArticleByTitle(queryInfo.getQuerytext());
       }else return articleMapper.QueryAllArticle();
    }

    @Override
    public String getContent(int article_id){
        return articleMapper.GetContentByID(article_id);
    }

    @Override
    public int addArticle(Article article){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date article_time = new Date();
        article.setArticle_time(sdFormat.format(article_time).toString());
        return articleMapper.AddArticle(article);
    }
    @Override
    public  int deleteArticle(int article_id){
        return articleMapper.DeleteArticle(article_id);
    }

    @Override
    public int AddArticleLike(int like_article_id,String like_user_name){
        int star = articleMapper.GetArticleStar(like_article_id);
        star = star +1;
        articleMapper.UpdateArticleStar(star,like_article_id);
        return articleMapper.AddArticleLike(like_article_id,like_user_name);
    }
    @Override
    public int DeleteArticleLike(int like_article_id,String like_user_name){
        int star = articleMapper.GetArticleStar(like_article_id);
        star = star - 1;
        articleMapper.UpdateArticleStar(star,like_article_id);
        return articleMapper.DeleteArticleLike(like_article_id,like_user_name);
    }
}
