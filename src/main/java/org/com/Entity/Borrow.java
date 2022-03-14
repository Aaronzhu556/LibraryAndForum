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
    public Borrow(){}
    public Borrow(int borrow_book_id, String borrow_start_time, String borrow_end_time,  String borrow_user_name,String borrow_user_temperature) {
        this.borrow_book_id = borrow_book_id;
        this.borrow_start_time = borrow_start_time;
        this.borrow_end_time = borrow_end_time;
        this.borrow_user_name = borrow_user_name;
        this.borrow_user_temperature = borrow_user_temperature;
    }
}
