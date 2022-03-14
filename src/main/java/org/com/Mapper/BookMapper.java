package org.com.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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
}
