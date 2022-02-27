package org.com.Service.Interface;

import org.com.Entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    public List<Book> QueryBookByName(String book_name);
    public List<Book> QueryBookAll();
}
