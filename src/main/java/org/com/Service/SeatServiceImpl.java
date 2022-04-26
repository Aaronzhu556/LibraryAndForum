package org.com.Service;

import org.com.Entity.Seat;
import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatInfoMapper;
import org.com.Mapper.SeatMapper;
import org.com.Service.Interface.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {
    @Autowired
    private SeatMapper seatMapper;
    @Autowired
    private SeatInfoMapper seatInfoMapper;

    @Override
    public List<Seat> GetAllSeat(){
       return seatMapper.QueryAllSeat();
    }

    @Override
    public List<Seat> GetAllSeatByQuery(){
        List<Seat> seatList = seatMapper.QueryAllSeat();
        for (Seat seat:seatList){
            List<SeatInfo> seatInfoList = seatInfoMapper.GetAllSeatInfoBySeatId(seat.getSeat_id());


            int seat_all = seat.getSeat_col() * seat.getSeat_row();
            System.out.println(seat_all);
            double ratio = seatInfoList.size()/seat_all,ratio_temp=0;

            BigDecimal bigDecimal = new BigDecimal(ratio);
            ratio_temp = bigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
            seat.setSeat_people_ratio(ratio_temp);
        }
        return seatList;
    }
    @Override
    public int DeleteSeat(int seat_id){
        seatMapper.DeleteSeat(seat_id);
        return seatInfoMapper.DeleteSeatinfoBySeatId(seat_id);
    }
    @Override
    public int AddSeat(Seat seat){
        return seatMapper.AddSeat(seat);
    }
}
