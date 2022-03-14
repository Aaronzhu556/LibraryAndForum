package org.com.Redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisServer {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void putValue(String key, String value){
        stringRedisTemplate.opsForValue().set(key, value,60, TimeUnit.MINUTES);
    }
    public String getValue(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }




}
