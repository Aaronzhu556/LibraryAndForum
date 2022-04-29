package org.com.Service.Interface;

import org.com.Entity.Article;
import org.com.Entity.Reply;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReplyService {
    public int AddReply(Reply reply);
    public int DeleteReply(int reply_id);
    public List<Reply>GetAllUnReadReply(String user_name);
    public Article ReadReply(int reply_id,int comment_id);
    public void ReadAllReply(List<Integer> replyIdList);
}
