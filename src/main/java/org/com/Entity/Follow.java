package org.com.Entity;

import lombok.Data;

@Data
public class Follow {
    private int follow_id;
    private String follow_user_from;
    private String follow_user_to;

    Follow(){}

    public Follow(String follow_user_from, String follow_user_to) {
        this.follow_user_from = follow_user_from;
        this.follow_user_to = follow_user_to;
    }
}
