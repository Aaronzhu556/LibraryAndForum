package org.com.Service.Interface;

import org.com.Entity.Comment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    List<Comment> QueryAllComment(int comment_article_id);

    int AddNewComment(Comment comment);

    int DeleteComment(int comment_id);
}
