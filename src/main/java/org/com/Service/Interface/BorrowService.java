package org.com.Service.Interface;

import org.com.Entity.Borrow;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BorrowService {
    public String AddBookBorrowInfo(String user_name,int book_id,String temperature);
    public int UpdateBookCommentInfo(String borrow_comment_context,int borrow_comment_star,int borrow_id);
    public List<Borrow> QueryAllBorrowWithoutComment(String user_name);

    public List<Borrow> QueryAllCommentById(int book_id);

}
