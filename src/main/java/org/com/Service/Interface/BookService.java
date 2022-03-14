package org.com.Service.Interface;

import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookService {
    public List<Book> QueryBook(String book_name);
    public String BorrowBook(int book_id,String user_name);
    public String DeleteBook(int book_id);

}
