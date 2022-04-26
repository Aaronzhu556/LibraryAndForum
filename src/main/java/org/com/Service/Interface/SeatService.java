package org.com.Service.Interface;

import org.com.Entity.Seat;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatService {

    public List<Seat> GetAllSeat();

    public List<Seat> GetAllSeatByQuery();


    public int DeleteSeat(int seat_id);

    public int AddSeat(Seat seat);


}
