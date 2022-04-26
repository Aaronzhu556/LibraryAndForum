package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.com.Entity.Borrow;
import org.com.Entity.Comment;

import java.util.List;

@Mapper
public interface BorrowMapper {


    @Insert("insert into t_borrow (borrow_book_id,borrow_start_time,borrow_end_time,borrow_end_time_true,borrow_user_name,borrow_user_temperature) values(#{borrow_book_id} ,#{borrow_start_time}, #{borrow_end_time} ,#{borrow_end_time_true},#{borrow_user_name},#{borrow_user_temperature})")
    public int AddBorrowBook_info(Borrow borrow);

    @Select("select borrow_book_id,borrow_end_time from t_borrow where borrow_user_name=#{user_name} and borrow_end_time_true is null")
    public List<Borrow> GetAllBookIdByUser(String user_name);

    @Select("select borrow_end_time from t_borrow where borrow_book_id=#{book_id}")
    public String GetBorrowEndTimeByBook(int book_id);

    @Update("update t_borrow set borrow_end_time_true=#{borrow_end_time_true} where borrow_user_name=#{borrow_user_name} and borrow_book_id=#{borrow_book_id} and borrow_end_time_true is null")
    public int ChangeBorrowInfo(String borrow_end_time_true,String borrow_user_name,int borrow_book_id);

    @Select("select borrow_book_id from t_borrow where borrow_end_time_true is not null and borrow_comment_context is null and borrow_user_name=#{borrow_user_name}")
    public List<Integer> QueryNoCommentBorrow(String borrow_user_name);

    @Update("update t_borrow set borrow_comment_context=#{borrow_comment_context},borrow_comment_time=#{borrow_comment_time},borrow_comment_star =#{borrow_comment_star} where borrow_id=#{borrow_id}")
    public int AddCommentBookInfo(String borrow_comment_context,String borrow_comment_time,int borrow_comment_star,int borrow_id);

    @Select("select * from t_borrow where borrow_end_time_true is not null and borrow_comment_context is null and borrow_user_name=#{borrow_user_name}")
    public List<Borrow> QueryBorrowWithoutComment(String borrow_user_name);

    @Select("select borrow_comment_star from t_borrow where borrow_book_id=#{book_id}")
    public List<Integer> QueryStarById(int book_id);

    @Select("select * from t_borrow where borrow_book_id=#{book_id} and borrow_comment_context is not null")
    public List<Borrow> QueryCommentById(int book_id);

    @Select("select borrow_id from t_borrow where borrow_book_id=#{book_id}")
    public List<Integer>GetBorrowIdByBook(int book_id);

    @Select("select * from t_borrow")
    public List<Borrow> GetAllBorrow();
}
