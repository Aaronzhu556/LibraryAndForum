package org.com.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Seat {
    private int seat_id;
    private int seat_row;
    private int seat_col;
    private String seat_name;
    private List<SeatInfo> seat_info;

    private double seat_people_ratio;

    public Seat(){};
    public Seat(int seat_id, int seat_row, int seat_col,String seat_name) {
        this.seat_id = seat_id;
        this.seat_row = seat_row;
        this.seat_col = seat_col;
        this.seat_name = seat_name;
    }

}
