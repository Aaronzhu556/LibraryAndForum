//package org.com.Service;
//
//import org.com.Entity.Borrow;
//import org.com.Mapper.BookMapper;
//import org.com.Mapper.BorrowMapper;
//import org.com.Service.Interface.BorrowService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//
//@Service
//public class BorrowServiceImpl implements BorrowService {
//
//    @Autowired
//    private BorrowMapper borrowMapper;
//
//    @Override
//    public String AddBookBorrowInfo(String user_name,int book_id){
//
//        SimpleDateFormat sdFormat=new SimpleDateFormat("yyyy年MM月dd日");
//        Calendar calendar_start = Calendar.getInstance();
//        Calendar calendar_end = Calendar.getInstance();
//        calendar_end.add(calendar_start.DATE,10);
//        Borrow borrow = new Borrow(book_id,sdFormat.format(calendar_start.getTime()),sdFormat.format(calendar_end),user_name);
//
//
//        int i = borrowMapper.AddBorrowBook_info(borrow);
//        if (i!=0) return "200";
//        else return "201";
//    }
//
//}
