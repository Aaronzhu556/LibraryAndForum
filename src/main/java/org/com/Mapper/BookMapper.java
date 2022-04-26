package org.com.Mapper;

import org.apache.ibatis.annotations.*;
import org.com.Entity.Book;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from t_book where book_name=#{book_name}")
    public List<Book> QueryBookByName(String book_name);

    @Select("select * from t_book")
    public List<Book> QueryBookAll();

    @Select("select * from t_book where book_id=#{book_id}")
    public Book QueryBookById(int book_id);

    @Update("update t_book set book_status=#{book_status} where book_id=#{book_id}")
    public int UpdateBookStatus(int book_id,String book_status);

    @Delete("delete from t_book where book_id=#{book_id}")
    public int DeleteBook(int book_id);

    @Select("select * from t_book where book_id=#{book_id}")
    public Book GetAllBookByUser(int book_id);

    @Insert("insert into t_book(book_name,book_img,book_isbn,book_money,book_category,book_address,book_context,book_author) values(#{book_name},#{book_img},#{book_isbn},#{book_money},#{book_category},#{book_address},#{book_context},#{book_author})")
    public int AddBook(Book book);

    @Select("select book_name from t_book where book_id=#{book_id}")
    public String QueryBookName(int book_id);

    @Select("select * from t_book where book_category=#{category_id}")
    public List<Book> GetAllBookByCategory(int category_id);



}
