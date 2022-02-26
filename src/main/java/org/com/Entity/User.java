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
    private String user_speak_status;
    private String user_appointment_status;
    private String user_reported_num;
    private String user_warn_num;

}
