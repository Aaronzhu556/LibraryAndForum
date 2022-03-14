package org.com;


import org.com.Redis.RedisServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisServerTest {

    @Autowired
    private RedisServer redisServer;

    @Test
    public void testRedis(){
        redisServer.putValue("zhu","123");
        System.out.println(redisServer.getValue("zhu"));
    }
}
