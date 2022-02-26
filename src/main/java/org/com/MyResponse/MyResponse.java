package org.com.MyResponse;

import lombok.Data;

@Data
public class MyResponse {
    private String code;
    private String msg;
    public MyResponse(){};
    public MyResponse(String code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
