package org.com.Service.Interface;

import org.com.Entity.Book;
import org.com.Entity.Borrow;
import org.com.Entity.QueryInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface BookService {
    public List<Book> QueryBook(QueryInfo queryInfo);
    public String BorrowBook(int book_id,String user_name);
    public String DeleteBook(int book_id);

    public List<Book> GetAllBookByUser(String  user_name);

    public int ReturnBook(String user_name,int book_id);

    public String UploadBookImg(MultipartFile multipartFile);

    public int AddBook(Book book);

    public List<Book> GetAllBookWithoutComment(String user_name);

    public List<Book> GetNewBook();


    public Book GetBookDetails(int book_id);

    public List<Book> GetAllBookByCategory(int category_id);

    public List<Book> GetAllBookByBorrow();

    public List<Book> GetAllBookByStar();
    public List<Book> GetAllBookByTime();

    public int GetAllBookNum();
}
