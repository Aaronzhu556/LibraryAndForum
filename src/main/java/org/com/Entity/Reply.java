package org.com.Entity;

import lombok.Data;

@Data
public class Reply {
    private int reply_id;
    private String reply_from;
    private String reply_to;
    private String reply_content;
    private String reply_time;
    private int reply_comment_id;


    private String reply_from_img;
    Reply(){}

    public Reply(String reply_from, String reply_to, String reply_content, String reply_time, int reply_comment_id) {
        this.reply_from = reply_from;
        this.reply_to = reply_to;
        this.reply_content = reply_content;
        this.reply_time = reply_time;
        this.reply_comment_id = reply_comment_id;
    }
}
