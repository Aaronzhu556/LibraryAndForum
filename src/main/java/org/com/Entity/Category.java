package org.com.Entity;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class Category {
    private int category_id;
    private String category_name;
    private int category_parent;
    private String category_status;
    private String category_level;

    private List<Category> category_children=new LinkedList<>();

    public void setChildren(List<Category> categories){
        this.category_children.addAll(categories);
    }

    public Category(int category_id, String category_name, int category_parent, String category_status, String category_level) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.category_parent = category_parent;
        this.category_status = category_status;
        this.category_level = category_level;
    }
}
