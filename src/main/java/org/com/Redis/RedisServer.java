package org.com.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Repository
public class RedisServer {
    @Autowired
    private StringRedisTemplate stringredisTemplate;

    public void putValue(String key, String value){
        stringredisTemplate.opsForValue().set(key, value,60, TimeUnit.MINUTES);
    }
    public String getValue(String key){
        return  stringredisTemplate.opsForValue().get(key);
    }

    public Set<String>  getAllKeys(){
        return stringredisTemplate.keys("*");
    }
    public Long getExpireTime(String key){
        return stringredisTemplate.getExpire(key,TimeUnit.MINUTES);
    }

    public Boolean deleteKey(String key){
        boolean flag = false;
        try{
            stringredisTemplate.delete(key);
            System.out.println(getValue(key));
            flag = true;
        }catch (Exception e){
            System.out.println(e);
        }
        return flag;
    }



}
