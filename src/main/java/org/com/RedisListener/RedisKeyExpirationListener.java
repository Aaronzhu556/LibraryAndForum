package org.com.RedisListener;

import lombok.extern.slf4j.Slf4j;

import org.com.Mapper.SeatInfoMapper;
import org.springframework.data.redis.connection.Message;
import org.com.Mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private SeatInfoMapper seatInfoMapper;
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message,byte[] pattern){
        System.out.println("监听到key已经过期");

        try{
            String expiredKey  = message.toString();
            bookMapper.UpdateBookStatus(Integer.parseInt(expiredKey),"1");//把书籍释放为可预约状态
            log.info("======================"+expiredKey+"号书籍已释放========================");
        }catch (Exception e){
            String  string[] = message.toString().split(",");
            System.out.println(string[0]+"过期了！！！！！！！！！！！！！！！！！！");
            seatInfoMapper.DeleteSeatInfo(Integer.parseInt(string[0]),Integer.parseInt(string[1]),Integer.parseInt(string[2]));
            log.info("======================("+string[0]+","+string[1]+","+string[2]+")号座位已释放========================");

        }










    }

}
