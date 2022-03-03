package org.com.MyResponse;

import lombok.Data;

@Data
public class MyResponse {
    private String code;
    private String msg;
    private String info;
    private Object object;
    private String page;
    public MyResponse(){};
    public MyResponse(String code,String msg,String info,Object object,String page){
        this.code = code;
        this.msg = msg;
        this.info = info;
        this.object = object;
        this.page = page;
    }
}
