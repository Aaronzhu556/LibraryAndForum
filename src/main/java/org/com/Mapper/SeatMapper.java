package org.com.Mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.com.Entity.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Mapper
public interface SeatMapper {
    @Select("select * from t_seat where seat_id=#{seat_id}")
    public Seat QuerySeatBUId(int seat_id);

    @Select("select * from t_seat")
    public List<Seat> QueryAllSeat();
    @Delete("delete from t_seat where seat_id=#{seat_id}")
    public int DeleteSeat(int seat_id);

    @Insert("insert into t_seat(seat_row,seat_col,seat_name) values(#{seat_row},#{seat_col},#{seat_name})")
    public int AddSeat(Seat seat);
}
