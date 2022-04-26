package org.com.Entity;

import lombok.Data;

@Data
public class SeatInfo {
    private int seatinfo_id;
    private int seatinfo_row;
    private int seatinfo_col;
    private int seatinfo_parent;
    private String seatinfo_user_name;
    private String seatinfo_time;
    private int seatinfo_isarrive;
    private String seatinfo_time_end;
    private int seatinfo_isleft;

    public SeatInfo(){};

    public SeatInfo(int seatinfo_row, int seatinfo_col, int seatinfo_parent, String seatinfo_user_name,  int seatinfo_isarrive) {
        this.seatinfo_row = seatinfo_row;
        this.seatinfo_col = seatinfo_col;
        this.seatinfo_parent = seatinfo_parent;
        this.seatinfo_user_name = seatinfo_user_name;
        //this.seatinfo_time = seatinfo_time;
        this.seatinfo_isarrive = seatinfo_isarrive;
    }

    public SeatInfo(int seatinfo_row, int seatinfo_col, int seatinfo_parent) {
        this.seatinfo_row = seatinfo_row;
        this.seatinfo_col = seatinfo_col;
        this.seatinfo_parent = seatinfo_parent;
    }
}
