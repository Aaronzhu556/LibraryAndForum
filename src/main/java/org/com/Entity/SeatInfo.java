package org.com.Entity;

import lombok.Data;

@Data
public class SeatInfo {
    private int seatinfo_id;
    private int seatinfo_row;
    private int seatinfo_col;
    private int seatinfo_parent;

    public SeatInfo(){};

    public SeatInfo(int seatinfo_row, int seatinfo_col, int seatinfo_parent) {
        this.seatinfo_row = seatinfo_row;
        this.seatinfo_col = seatinfo_col;
        this.seatinfo_parent = seatinfo_parent;
    }
}
