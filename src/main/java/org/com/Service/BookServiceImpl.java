package org.com.Service;

import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.Mapper.BookMapper;
import org.com.Service.Interface.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Override
    public List<Book> QueryBookByName(String book_name){
        List<Book> books = bookMapper.QueryBookByName(book_name);
        return null;
    }
    @Override
    public List<Book> QueryBookAll(){
        return  bookMapper.QueryBookAll();
    }
}
