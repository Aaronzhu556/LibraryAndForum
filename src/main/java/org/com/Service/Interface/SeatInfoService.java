package org.com.Service.Interface;

import org.com.Entity.SeatData;
import org.com.Entity.SeatInfo;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public interface SeatInfoService {
    public List<SeatInfo> QuerySeatInfo(int seat_id);
    public void AddSeatInfo(String seatInfo,String user_name);
    public int SigninSeatInfo(SeatInfo seatInfo,String user_name);
    public List<SeatInfo> GetSeatInfoByUser(String user_name);
    public int SingoutSeatInfo(int seatinfo_id);

    public List<SeatData> GetAllSeatInfo() throws ParseException;

}
