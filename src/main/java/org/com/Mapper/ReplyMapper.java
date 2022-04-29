package org.com.Mapper;

import org.apache.ibatis.annotations.*;
import org.com.Entity.Reply;

import java.util.List;

@Mapper
public interface ReplyMapper {

    @Select("select * from t_reply where reply_comment_id=#{reply_comment_id}")
    public List<Reply> GetAllReplyById(int reply_comment_id);

    @Insert("insert into t_reply(reply_from,reply_to,reply_content,reply_time,reply_comment_id) values(#{reply_from},#{reply_to},#{reply_content},#{reply_time},#{reply_comment_id})")
    public int AddNewReply(Reply reply);

    @Delete("delete from t_reply where reply_id=#{reply_id}")
    public int DeleteReply(int reply_id);

    @Delete("delete from t_reply where reply_comment_id=#{reply_comment_id}")
    public int DeleteReplyByComment(int reply_comment_id);

    @Select("select reply_comment_id from t_reply where reply_id=#{reply_id}")
    public int GetReplyCommentId(int reply_id);

    @Select("select * from t_reply where reply_to=#{user_name} and reply_status='0'")
    public List<Reply> GetAllUnReadReply(String user_name);

    @Update("update t_reply set reply_status=#{reply_status} where reply_id=#{reply_id}")
    public int ReadReply(String reply_status,int reply_id);

}
