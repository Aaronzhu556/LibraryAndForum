package org.com.Entity;

import lombok.Data;

@Data
public class Notice {
    private int notice_id;
    private String notice_title;
    private String notice_content;
    private String notice_time;
    Notice(){};

    public Notice(String notice_title, String notice_content, String notice_time) {
        this.notice_title = notice_title;
        this.notice_content = notice_content;
        this.notice_time = notice_time;
    }
}
