package org.com.Mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.com.Entity.Seat;
import org.com.Entity.SeatInfo;

import java.util.List;

@Mapper
public interface SeatInfoMapper {

    @Select("select * from t_seatinfo where seatinfo_parent=#{seat_id}")
    public List<SeatInfo>QuerySeatInfo(int seat_id);
    @Insert("insert into t_seatinfo(seatinfo_row,seatinfo_col,seatinfo_parent) values(#{seatinfo_row},#{seatinfo_col},#{seatinfo_parent})")
    public int AddSeatInfo(SeatInfo seatInfo);

}
