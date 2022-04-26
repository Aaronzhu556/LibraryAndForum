package org.com.Service;

import org.com.Entity.Article;
import org.com.Entity.QueryInfo;
import org.com.Mapper.*;
import org.com.Service.Interface.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private FollowMapper followMapper;

    @Override
    public List<Article> getAllArticle(QueryInfo queryInfo){
       if (!queryInfo.getQuerytext().equals("")){

           return articleMapper.QueryArticleByTitle(queryInfo.getQuerytext());
       }else{

           return articleMapper.QueryAllArticle();
       }
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
        int i = articleMapper.DeleteArticle(article_id);
        int flag=0;
        if (i!=0) flag = commentMapper.deleteCommentByArticle(article_id);
        return i;
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

    @Override
    public List<Article> GetAllArticleByUser(String article_user_name){
        return articleMapper.GetAllArticleByUser(article_user_name);
    }
    @Override
    public int UpdateArticleClick(int article_id){
        int click = articleMapper.GetArticleClick(article_id);
        click = click + 1;
        return articleMapper.UpdateArticleClick(click,article_id);
    }
    /*
    * created by ChenX
    * 热度推荐算法
    * H = (W+I)/(T+1)^G   这是总计算公式
    * W:帖子的热度  T:发帖人的热度   T:发帖时间  G:重力因子  取1.8
    * W是帖子的热度，由用户的点赞、评论、查看加权计算得出。我取的权重分别是5 3 2
    * I是发帖人的热度   由发帖数和粉丝和用户所获得的点赞数加权得出  权重是1 5  4
    *
    * T是发帖到现在一共多少个小时    G是重力因子 取1.8
    * */
    @Override
    public List<Article> GetHotArticle(){
        List<Article> articles = articleMapper.QueryAllArticle();
        List<Article> temp = articles.stream().collect(Collectors.toList());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        for (Article article:articles){
            int click=0,reply=0,like=0; //0.1  0.5   0.4
            int user_article_num=0,user_fans=0,user_like=0;//0.1   0.5   0.4

            double W=0, I=0,G=1.8 ,T=0,H=0;

            W=article.getArticle_click()*2 + article.getArticle_reply_num()*5 + article.getArticle_star()*3;
            user_article_num = articleMapper.GetAllArticleByUser(article.getArticle_user_name()).size();
            user_fans = followMapper.GetUserFans(article.getArticle_user_name()).size();
            for (int i=0;i<temp.size();i++){
                if (temp.get(i).getArticle_user_name()==article.getArticle_user_name()) user_like = user_like +1;
            }
            I = user_article_num+ user_fans*5+user_like*4;

            ParsePosition position= new ParsePosition(0);
            Date date_end = formatter.parse(article.getArticle_time(),position);
            Date date_now = new Date();

            long diff = date_now.getTime() - date_end.getTime();
            T=  diff % nd/nh;

            H=(W+I)/Math.pow(T+1,G);
            article.setArticle_hot(H);
        }
        articles.sort(Comparator.comparing(Article::getArticle_hot).reversed());  //最后根据热度进行排序  用户的每一次点赞评论点击查看都会影响到热度
       // System.out.println(articles.toString());
        return articles;

    }
}
/**
 * 本周主要做
 *
 * */
