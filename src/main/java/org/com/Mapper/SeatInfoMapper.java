package org.com.Mapper;

import org.apache.ibatis.annotations.*;
import org.com.Entity.Seat;
import org.com.Entity.SeatInfo;

import java.util.List;

@Mapper
public interface SeatInfoMapper {

    @Select("select * from t_seatinfo where seatinfo_parent=#{seat_id} and seatinfo_isleft=0")
    public List<SeatInfo>QuerySeatInfo(int seat_id);
    @Insert("insert into t_seatinfo(seatinfo_row,seatinfo_col,seatinfo_parent,seatinfo_user_name) values(#{seatinfo_row},#{seatinfo_col},#{seatinfo_parent},#{seatinfo_user_name})")
    public int AddSeatInfo(SeatInfo seatInfo);

    @Delete("delete from t_seatinfo where seatinfo_row=#{seatinfo_row} and seatinfo_col=#{seatinfo_col} and seatinfo_parent=#{seatinfo_parent}")
    public int DeleteSeatInfo(int seatinfo_row,int seatinfo_col,int seatinfo_parent);

    @Update("update t_seatinfo set seatinfo_isarrive=#{seatinfo_isarrive} where seatinfo_id=#{seatinfo_id} ")
    public int UpdateSeatinfoStatus(int seatinfo_id, int seatinfo_isarrive);

    @Update("update t_seatinfo set seatinfo_time=#{seatinfo_time} where seatinfo_id=#{seatinfo_id}")
    public int UpdateSeatInfoTime(String seatinfo_time,int seatinfo_id);

    @Select("select * from t_seatinfo where seatinfo_user_name=#{user_name}")
    public List<SeatInfo> GetSeatInfoByUser(String user_name);

    @Update("update t_seatinfo set seatinfo_time_end=#{seatinfo_time_end} where seatinfo_id=#{seatinfo_id}")
    public int UpdateSeatInfoTimeEnd(String seatinfo_time_end,int seatinfo_id);

    @Update("update t_seatinfo set seatinfo_isleft=#{seatinfo_isleft} where seatinfo_id=#{seatinfo_id}")
    public int UpdateSeatInfoIsLeft(int  seatinfo_isleft,int seatinfo_id);

    @Select("select * from t_seatinfo where seatinfo_parent=#{seatinfo_parent} and seatinfo_isarrive=1 and seatinfo_isleft=0")
    public List<SeatInfo> GetAllSeatInfoBySeatId(int seatinfo_parent);

    @Delete("delete from t_seatinfo where seatinfo_parent=#{seat_id}")
    public int DeleteSeatinfoBySeatId(int seat_id);

    @Select("select * from t_seatinfo where seatinfo_time is not null")
    public List<SeatInfo> GetAllSeatInfo();

}
