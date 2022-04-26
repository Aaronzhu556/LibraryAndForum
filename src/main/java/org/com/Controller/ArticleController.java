package org.com.Controller;

import org.com.Entity.Article;
import org.com.Entity.Comment;
import org.com.Entity.QueryInfo;
import org.com.Mapper.CommentMapper;
import org.com.Mapper.LikeMapper;
import org.com.Mapper.UserMapper;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.ArticleService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @RequestMapping("/getallarticle")
    public MyResponse getALlArticle(@RequestBody QueryInfo queryInfo, @RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Article> allArticle = articleService.getAllArticle(queryInfo);
            List<Article> ArticleList = new LinkedList<>();
            int page = 0;
            if (!allArticle.isEmpty()){

                int count = 0;
                if (!queryInfo.getQuerydata().equals(queryInfo.getQuerytext())&&queryInfo.getPagenum()!=1) {
                    for (int i = 0; count < queryInfo.getPagesize(); i++) {
                        try {
                            ArticleList.add(allArticle.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    page = 1;
                }else {
                    for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                        try {
                            System.out.println("caocao coaocoao");
                            ArticleList.add(allArticle.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
                List<Integer> Likelist = likeMapper.GetAllArticleLike(JwtUtil.GetInformation(token));
                for (int i=0;i<ArticleList.size();i++) {
                    ArticleList.get(i).setArticle_user_img("/api" + userMapper.GetUserImgByName(ArticleList.get(i).getArticle_user_name()));
                    if (Likelist.contains(ArticleList.get(i).getArticle_id())){
                        ArticleList.get(i).setArticle_user_like(true);
                    }else ArticleList.get(i).setArticle_user_like(false);
                }

//                for (int i=0;i<ArticleList.size();i++){
//                    List<Comment> commentList = commentMapper.QueryAllCommentByArticleId(ArticleList.get(i).getArticle_id());
//                    ArticleList.get(i).setArticle_reply_num(commentList.size());
//                }

                return new MyResponse("200","查询成功",String.valueOf(allArticle.size()),ArticleList,String.valueOf(page));
            }else return new MyResponse("201","没有您搜索的信息~","",null,"");
        }else return new MyResponse("202","JWt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getcontent")
    public String getContentById(@RequestParam int article_id){
        return articleService.getContent(article_id);
    }

    @ResponseBody
    @RequestMapping("/addarticle")
    public MyResponse addArticle(@RequestBody Article article,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            int i = articleService.addArticle(article);
            if (i!=0)return new MyResponse("200","发帖成功","",null,"");
            else return new MyResponse("201","发帖失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @RequestMapping("/deletearticle")
    @ResponseBody
    public MyResponse deleteArticle(@RequestParam int article_id,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            int flag = articleService.deleteArticle(article_id);
            if (flag!=0) return new MyResponse("200","删除成功","",null,"");
            else return new MyResponse("201","删除失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");

    }

    @ResponseBody
    @RequestMapping("/addlikearticle")
    public MyResponse AddLikeArticle(@RequestParam int article_id,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            int i = articleService.AddArticleLike(article_id, JwtUtil.GetInformation(token));
            if (i!=0) return new MyResponse("200","增添成功","",null,"");
            else return new MyResponse("201","增添失败","",null,"");

        }else return new MyResponse("202","Jwt验证失败","",null,"");

    }
    @ResponseBody
    @RequestMapping("/deletelikearticle")
    public MyResponse DeleteArticle(@RequestParam int article_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = articleService.DeleteArticleLike(article_id,JwtUtil.GetInformation(token));
            if (i!=0) return new MyResponse("200","增添成功","",null,"");
            else return new MyResponse("201","","",null,"");
        }else return new MyResponse("202","","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getallarticlebyuser")
    public MyResponse GetAllArticleByUser(@RequestParam String article_user_name,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Article> articles = articleService.GetAllArticleByUser(article_user_name);
            List<Integer> Likelist = likeMapper.GetAllArticleLike(JwtUtil.GetInformation(token));
            for (int i=0;i<articles.size();i++) {
                if (Likelist.contains(articles.get(i).getArticle_id())){
                    articles.get(i).setArticle_user_like(true);
                }else articles.get(i).setArticle_user_like(false);
            }

            if (!articles.isEmpty()) return new MyResponse("200","查询成功","",articles,"");
            else return new MyResponse("201","此用户还没有发帖","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");

    }
    @ResponseBody
    @RequestMapping("/addclicknum")
    public MyResponse AddClickNum(@RequestParam int article_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = articleService.UpdateArticleClick(article_id);
            return new MyResponse("200","记录成功","",null,"");
        }else   return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/gethotarticle")
    public MyResponse GetHotArticle(@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Article> articles = articleService.GetHotArticle();
            return new MyResponse("200","分析成功","",articles.subList(0,5),"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }


}
