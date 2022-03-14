package org.com.RedisListener;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.redis.connection.Message;
import org.com.Mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
    @Autowired
    private BookMapper bookMapper;
    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }

    @Override
    public void onMessage(Message message,byte[] pattern){
        String expiredKey  = message.toString();
        System.out.println("监听到key已经过期");
        bookMapper.UpdateBookStatus(Integer.parseInt(expiredKey),"1");//把书籍释放为可预约状态
        log.info("======================"+expiredKey+"号书籍已释放========================");

    }

}
