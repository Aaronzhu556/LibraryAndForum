package org.com.Entity;

import lombok.Data;

@Data
public class Manager {
    private String manager_name;
    private String manager_password;

    public Manager(){}
    public Manager(String manager_name, String manager_password){
        this.manager_name= manager_name;
        this.manager_password = manager_password;
    }
}
