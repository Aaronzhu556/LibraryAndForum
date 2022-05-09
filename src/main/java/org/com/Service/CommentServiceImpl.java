package org.com.Service;

import org.com.Entity.Comment;
import org.com.Entity.Reply;
import org.com.Mapper.ArticleMapper;
import org.com.Mapper.CommentMapper;
import org.com.Mapper.ReplyMapper;
import org.com.Mapper.UserMapper;
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
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;


    @Override
    public List<Comment> QueryAllComment(int comment_article_id){
        List<Comment> commentList = commentMapper.QueryAllCommentByArticleId(comment_article_id);

        for (Comment comment:commentList){
            List<Reply> replies = replyMapper.GetAllReplyById(comment.getComment_id());
            for (Reply reply:replies){

                reply.setReply_from_img( userMapper.GetUserImgByName(reply.getReply_from()));
            }
            comment.setComment_input_show(false);
            comment.setComment_reply_list(replies);
            comment.setComment_user_img( userMapper.GetUserImgByName(comment.getComment_user_name()));
            comment.setComment_box_show(false);
        }
        return commentList;

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

    @Override
    public int DeleteComment(int comment_id){
        int num = replyMapper.DeleteReplyByComment(comment_id);
        int article_id = commentMapper.GetCommentArticle_id(comment_id);
        articleMapper.UpdateArticleReplyNum(num+1,article_id);

        return commentMapper.deleteCommentById(comment_id);
    }
}
