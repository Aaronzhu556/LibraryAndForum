package org.com.Service.Interface;

import org.com.Entity.SeatInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SeatInfoService {
    public List<SeatInfo> QuerySeatInfo(int seat_id);
    public void AddSeatInfo(String seatInfo);
}
