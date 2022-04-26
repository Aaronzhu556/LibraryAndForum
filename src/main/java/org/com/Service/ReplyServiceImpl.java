package org.com.Service;

import org.com.Entity.Reply;
import org.com.Mapper.ArticleMapper;
import org.com.Mapper.CommentMapper;
import org.com.Mapper.ReplyMapper;
import org.com.Service.Interface.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private CommentMapper commentMapper;


    @Override
    public int AddReply(Reply reply){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        reply.setReply_time(sdFormat.format(date));
        int comment_id=reply.getReply_comment_id();
        int article_id=commentMapper.GetCommentArticle_id(comment_id);
        int replyNum = articleMapper.GetArticleReplyNum(article_id);
        replyNum = replyNum + 1;
        articleMapper.UpdateArticleReplyNum(replyNum,article_id);


        return replyMapper.AddNewReply(reply);

    }

    @Override
    public int DeleteReply(int reply_id){
        int comment_id=replyMapper.GetReplyCommentId(reply_id);
        int article_id=commentMapper.GetCommentArticle_id(comment_id);
        int replyNum = articleMapper.GetArticleReplyNum(article_id);
        replyNum = replyNum - 1;
        articleMapper.UpdateArticleReplyNum(replyNum,article_id);
        return replyMapper.DeleteReply(reply_id);

    }
}
