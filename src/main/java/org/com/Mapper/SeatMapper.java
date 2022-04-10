package org.com.Mapper;

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
}
