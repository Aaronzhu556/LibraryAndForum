package org.com.Service;

import org.com.Entity.Seat;
import org.com.Mapper.SeatMapper;
import org.com.Service.Interface.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatMapper seatMapper;

    @Override
    public List<Seat> GetAllSeat(){
       return seatMapper.QueryAllSeat();
    }
}
