package org.com.Entity;

import lombok.Data;

@Data
public class BookComment {

    private int book_comment_id;
    private String book_comment_context;
    private String book_comment_time;
    private String book_comment_user;
    private int book_id;

    public BookComment(){};
    public BookComment(String book_comment_context, String book_comment_time, String book_comment_user, int book_id) {
        this.book_comment_context = book_comment_context;
        this.book_comment_time = book_comment_time;
        this.book_comment_user = book_comment_user;
        this.book_id = book_id;
    }
}
