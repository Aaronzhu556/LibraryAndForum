package org.com.Service;

import org.com.Entity.Comment;
import org.com.Mapper.ArticleMapper;
import org.com.Mapper.CommentMapper;
import org.com.Service.Interface.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    public List<Comment> QueryAllComment(int comment_article_id){
        return commentMapper.QueryAllCommentByArticleId(comment_article_id);
    }

    @Override
    public int AddNewComment(Comment comment){
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date comment_time = new Date();

        comment.setComment_time(sdFormat.format(comment_time).toString());


        int flag = commentMapper.InsertComment(comment);
        int replyNum = articleMapper.GetArticleReplyNum(comment.getComment_article_id());
        replyNum = replyNum + 1;
        articleMapper.UpdateArticleReplyNum(replyNum,comment.getComment_article_id());
        return flag;
    }
}
