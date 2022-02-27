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
    private String user_address;

    private String user_status;
    private String user_speak_status;
    private String user_reported_num;
    private String user_warn_num;
    public User (){}

    public User(@Length(max = 10) String user_name, String user_password, @NotNull @Length(max = 11) String user_phone, String user_address, String user_status, String user_speak_status, String user_reported_num, String user_warn_num) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_phone = user_phone;
        this.user_address = user_address;
        this.user_status = user_status;
        this.user_speak_status = user_speak_status;
        this.user_reported_num = user_reported_num;
        this.user_warn_num = user_warn_num;
    }
}
