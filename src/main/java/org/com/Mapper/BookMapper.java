package org.com.Mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.com.Entity.Book;
import java.util.LinkedList;
import java.util.List;

@Mapper
public interface BookMapper {
    @Select("select * from t_book where book_name=#{book_name}")
    public List<Book> QueryBookByName(String book_name);

    @Select("select & from t_book")
    public List<Book> QueryBookAll();
}
