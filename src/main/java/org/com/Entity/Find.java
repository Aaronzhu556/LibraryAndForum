package org.com.Entity;

import lombok.Data;

import java.util.List;

@Data
public class Find {
    private int find_id;
    private String find_name;
    private String find_details;
    private String find_time;
    private String find_img;
    private String find_status;
    private String find_level;

    private List<String> find_img_list;


    Find(){};

    public Find(String find_name, String find_details, String find_time, String find_img, String find_status,String find_level) {
        this.find_name = find_name;
        this.find_details = find_details;
        this.find_time = find_time;
        this.find_img = find_img;
        this.find_status = find_status;
        this.find_level = find_level;
    }
}
