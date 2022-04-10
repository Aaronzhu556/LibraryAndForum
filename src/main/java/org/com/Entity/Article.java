package org.com.Entity;


import lombok.Data;

import java.util.List;

@Data
public class Article {
    private int article_id;
    private String article_title;
    private String article_content;
    private int article_reply_num;
    private int article_star;

    private boolean article_user_like;

    private String article_reply;

    private List<Integer> article_reply_list;

    private String article_user_name;
    private String article_time;

    Article(){}

    public Article(int article_id, String article_title, String article_content, int article_reply_num, int article_star, String article_reply,String article_user_name,String article_time) {
        this.article_id = article_id;
        this.article_title = article_title;
        this.article_content = article_content;
        this.article_reply_num = article_reply_num;
        this.article_star = article_star;
        this.article_reply = article_reply;
        this.article_user_name = article_user_name;
        this.article_time = article_time;
    }

    public void setArticle_reply_list(List<Integer> article_reply_list) {
        this.article_reply_list = article_reply_list;
    }

    public void setArticle_user_like(boolean article_user_like) {
        this.article_user_like = article_user_like;
    }
}
