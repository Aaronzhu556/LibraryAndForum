package org.com.Entity;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class Book {
    @NotNull
    private int book_id;
    private String book_name;
    private String book_img;

    private String book_isbn;
    private String book_status;
    private String book_money;
    private int book_category;
    private String book_category_name;
    private String book_address;
    private String book_context;
    private String book_author;


    private double book_star;
    private List<BookComment> bookComments;


    private String book_borrow_end_time;

    private boolean book_is_expired;

    private int book_borrow_num;

    public void setBookComments(List<BookComment> bookComments) {
        this.bookComments = bookComments;
    }

    public void setBook_is_expired(boolean book_is_expired) {
        this.book_is_expired = book_is_expired;
    }

    public void setBook_borrow_end_time(String book_borrow_end_time) {
        this.book_borrow_end_time = book_borrow_end_time;
    }



    Book(){}
    public Book(String book_name, String book_img, String book_isbn, String book_status, String book_money, int book_category,String book_address,String book_context) {
        this.book_name = book_name;
        this.book_img = book_img;
        this.book_isbn = book_isbn;
        this.book_status = book_status;
        this.book_money = book_money;
        this.book_category = book_category;
        this.book_address = book_address;
        this.book_context = book_context;
    }
    public Book(String book_name, String book_img, String book_isbn, String book_status, String book_money, int book_category,String book_address,String book_context,String book_author) {
        this.book_name = book_name;
        this.book_img = book_img;
        this.book_isbn = book_isbn;
        this.book_status = book_status;
        this.book_money = book_money;
        this.book_category = book_category;
        this.book_address = book_address;
        this.book_context = book_context;
        this.book_author = book_author;
    }


}
