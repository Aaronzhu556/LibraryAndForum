package org.com.Entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Borrow {
    @NotNull
    private int borrow_id;
    private int borrow_book_id;
    private String borrow_start_time;
    private String borrow_end_time;
    private String borrow_end_time_true;
    private String borrow_user_name;
    private String borrow_user_temperature;


    private String borrow_book_name;



    private String borrow_comment_context;
    private String borrow_comment_time;
    private int borrow_comment_star;

    private String borrow_user_img;//仅用于中转数据

    public void setBorrow_book_name(String borrow_book_name) {
        this.borrow_book_name = borrow_book_name;
    }

    public Borrow(){}
    public Borrow(int borrow_book_id, String borrow_start_time, String borrow_end_time,  String borrow_user_name,String borrow_user_temperature) {
        this.borrow_book_id = borrow_book_id;
        this.borrow_start_time = borrow_start_time;
        this.borrow_end_time = borrow_end_time;
        this.borrow_user_name = borrow_user_name;
        this.borrow_user_temperature = borrow_user_temperature;
    }

    public Borrow(int borrow_book_id, String borrow_start_time, String borrow_end_time, String borrow_end_time_true, String borrow_user_name, String borrow_user_temperature, String borrow_comment_context, String borrow_comment_time, int borrow_comment_star) {
        this.borrow_book_id = borrow_book_id;
        this.borrow_start_time = borrow_start_time;
        this.borrow_end_time = borrow_end_time;
        this.borrow_end_time_true = borrow_end_time_true;
        this.borrow_user_name = borrow_user_name;
        this.borrow_user_temperature = borrow_user_temperature;
        this.borrow_comment_context = borrow_comment_context;
        this.borrow_comment_time = borrow_comment_time;
        this.borrow_comment_star = borrow_comment_star;
    }

    public void setBorrow_comment_context(String borrow_comment_context) {
        this.borrow_comment_context = borrow_comment_context;
    }

    public void setBorrow_comment_time(String borrow_comment_time) {
        this.borrow_comment_time = borrow_comment_time;
    }

    public void setBorrow_comment_star(int borrow_comment_star) {
        this.borrow_comment_star = borrow_comment_star;
    }
}
