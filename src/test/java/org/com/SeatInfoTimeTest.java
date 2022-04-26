package org.com;

import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatInfoMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
public class SeatInfoTimeTest {
    @Autowired
    private SeatInfoMapper seatInfoMapper;

    @Test
    public void GetAllSeatInfo() throws ParseException {
        List<SeatInfo> seatInfoList = seatInfoMapper.GetAllSeatInfo();
        List<Date> time_list = new LinkedList<>();
        Map<String,Integer> map = new HashMap<String, Integer>();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd ");
        Date date_now = new Date();
        seatInfoList.sort(Comparator.comparing(SeatInfo::getSeatinfo_id).reversed());
        long temp = 86400000;
        for (int i=6;i>=0;i--) {
            Date date_temp = new Date();
            long time1 = date_now.getTime()-(temp * i);
            date_temp.setTime(time1);
            time_list.add(date_temp);
            map.put(sdFormat.format(date_temp),0);
        }

        for (SeatInfo seatInfo: seatInfoList){
            Date date = sdFormat.parse(seatInfo.getSeatinfo_time_end());
            System.out.println(date.toString());
            for (int i=0;i<=6;i++){
                if (date.after(time_list.get(i))&&date.before(time_list.get(i+1))){
                    Integer integer_temp = map.get(sdFormat.format(date));
                    integer_temp = integer_temp +1;
                    System.out.println("加1");
                    System.out.println(integer_temp);
                    map.put(sdFormat.format(date),integer_temp);
                }

            }
        }

        System.out.println(map.toString());


    }
}
