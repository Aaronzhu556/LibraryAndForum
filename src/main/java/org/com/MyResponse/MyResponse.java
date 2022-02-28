package org.com.MyResponse;

import lombok.Data;

@Data
public class MyResponse {
    private String code;
    private String msg;
    private Object object;
    public MyResponse(){};
    public MyResponse(String code,String msg,Object object){
        this.code = code;
        this.msg = msg;
        this.object = object;
    }
}
