package org.com.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Lost {
    private int lost_id;
    private String lost_name;
    private String lost_details;
    private String lost_time;
    private String lost_img;
    private String lost_status;

    private List<String> lost_img_list;

    public Lost(){};

    public Lost(String lost_name, String lost_details, String lost_time, String lost_img, String lost_status) {
        this.lost_name = lost_name;
        this.lost_details = lost_details;
        this.lost_time = lost_time;
        this.lost_img = lost_img;
        this.lost_status = lost_status;
    }
}
