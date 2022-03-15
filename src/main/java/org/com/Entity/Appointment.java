package org.com.Entity;

import lombok.Data;

@Data
public class Appointment {

    private int appointment_id;
    private String appointment_book_id;
    private String appointment_user_name;
    private String appointment_expire_time;
    private String appointment_status;
    Appointment(){}

    public Appointment(int appointment_id, String appointment_book_id, String appointment_user_name, String appointment_expire_time,String appointment_status) {
        this.appointment_id = appointment_id;
        this.appointment_book_id = appointment_book_id;
        this.appointment_user_name = appointment_user_name;
        this.appointment_expire_time = appointment_expire_time;
        this.appointment_status = appointment_status;
    }
}
