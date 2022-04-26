package org.com.Entity;

import lombok.Data;

@Data
public class SeatData {
    private String seatinfo_date;
    private int seatinfo_num;

    SeatData(){};

    public SeatData(String seatinfo_date, int seatinfo_num) {
        this.seatinfo_date = seatinfo_date;
        this.seatinfo_num = seatinfo_num;
    }
}
