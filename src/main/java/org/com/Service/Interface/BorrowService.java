package org.com.Service.Interface;

import org.springframework.stereotype.Service;

@Service
public interface BorrowService {
    public String AddBookBorrowInfo(String user_name,int book_id,String temperature);
}
