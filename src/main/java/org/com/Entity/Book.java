package org.com.Entity;

import lombok.Data;

import javax.validation.constraints.NotNull;

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
    Book(){}
    public Book(String book_name, String book_img, String book_isbn, String book_status, String book_money, int book_category,String book_address) {
        this.book_name = book_name;
        this.book_img = book_img;
        this.book_isbn = book_isbn;
        this.book_status = book_status;
        this.book_money = book_money;
        this.book_category = book_category;
        this.book_address = book_address;
    }
}
