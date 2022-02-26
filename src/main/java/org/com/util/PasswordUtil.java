package org.com.util;

import org.springframework.util.DigestUtils;

public class PasswordUtil {
    /*
     * 私有化构造函数，不允许创建对象
     * */
    private PasswordUtil(){}

    private final static String salt="password_md5_salt_createdByAaron_2021.02.08_bagayalu_!@#)(*$&%^";

    /*
     * 使用md5加密
     * */
    private final static String md5(String password){
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    /*
     * 做密码验证
     * */
    private final static boolean isEmpty(String password){
        return password==null || password.isEmpty();
    }
    public final static String getPassword(String password){
        if (isEmpty(password)){
            //抛出错误
        }
        return md5(password);
    }
}
