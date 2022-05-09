package org.com.Entity;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class User {
    private int user_id;
    @Length(max=10)
    private String user_name;

    private String user_password;
    @NotNull
    @Length(max=11)
    private String user_phone;

    private String user_status;

    private String user_book_num;
    private String user_img;
    private String user_privacy;
    private String user_motto;
    private int user_hot;
    private int user_article_num;
    private int user_fans;
    private int user_great;




    public User (){}
    public User(@Length(max = 10) String user_name, String user_password, @NotNull @Length(max = 11) String user_phone, String user_status, String user_book_num, String user_img,String user_privacy,String user_motto) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_status = user_status;
        this.user_book_num = user_book_num;
        this.user_img = user_img;
        this.user_privacy = user_privacy;
        this.user_motto = user_motto;
    }
    public User(@Length(max = 10) String user_name, String user_password, @NotNull @Length(max = 11) String user_phone, String user_status, String user_book_num, String user_img) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_status = user_status;
        this.user_book_num = user_book_num;
        this.user_img = user_img;
    }

    public User(@Length(max = 10) String user_name, String user_password, @NotNull @Length(max = 11) String user_phone, String user_status, String user_book_num) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_status = user_status;

        this.user_book_num = user_book_num;
    }
}
