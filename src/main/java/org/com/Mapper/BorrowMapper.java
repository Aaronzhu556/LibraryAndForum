package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.com.Entity.Borrow;

@Mapper
public interface BorrowMapper {


    @Insert("insert into t_borrow (borrow_book_id,borrow_start_time,borrow_end_time,borrow_end_time_true,borrow_user_name) values(#{borrow_book_id} ,#{borrow_start_time}, #{borrow_end_time} ,#{borrow_end_time_true},#{borrow_user_name})")
    public int AddBorrowBook_info(Borrow borrow);


}
