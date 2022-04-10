package org.com.Entity;

import lombok.Data;

@Data
public class Comment {
    private int comment_id;
    private String comment_user_name;
    private String comment_time;
    private String comment_content;
    private int comment_article_id;
    public Comment(){};
    public Comment(String comment_user_name, String comment_time, String comment_content, int comment_article_id) {
        this.comment_user_name = comment_user_name;
        this.comment_time = comment_time;
        this.comment_content = comment_content;
        this.comment_article_id = comment_article_id;
    }
}
