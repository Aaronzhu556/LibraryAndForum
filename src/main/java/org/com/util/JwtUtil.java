package org.com.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created By Aaronzhu
 * 2022-2-27
 * Jwt验证
 * */
public class JwtUtil {

    private final static String key="abcdsd_20220227_CreatedByAaronzhu";
    private final static SecretKey secretKey = new SecretKeySpec(key.getBytes(), SignatureAlgorithm.HS256.getJcaName());

    /*
    * 返回token
    * */
    public final static String CreateToken(String username){
        return Jwts.builder().setSubject(username).signWith(secretKey).compact();
    }

    /*
    * 验证token
    * */
    public final static boolean VerifyToken(String token){
        boolean flag;
        try{
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            flag=true;
        }catch (Exception e){
            flag=false;
        }
        return flag;
    }
    /**
     * 通过token获取发送请求的用户信息 返回其用户名
     * */
    public final static String GetInformation(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
       return claimsJws.getBody().getSubject();
    }


}
