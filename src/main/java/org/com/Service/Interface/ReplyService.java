package org.com.Service.Interface;

import org.com.Entity.Reply;
import org.springframework.stereotype.Service;

@Service
public interface ReplyService {
    public int AddReply(Reply reply);
    public int DeleteReply(int reply_id);


}
