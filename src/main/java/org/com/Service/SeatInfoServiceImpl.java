package org.com.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatInfoMapper;
import org.com.Service.Interface.SeatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class SeatInfoServiceImpl implements SeatInfoService {
    @Autowired
    private SeatInfoMapper seatInfoMapper;
    @Override
    public List<SeatInfo> QuerySeatInfo(int seat_id){
        return seatInfoMapper.QuerySeatInfo(seat_id);
    }

    @Override
    public void AddSeatInfo(String seatInfo){
        List<SeatInfo> seatInfoList = new LinkedList<>();
        JSONObject jsonObject = JSON.parseObject(seatInfo);
        String str = jsonObject.getString("seatInfo_list");
        JSONArray jsonArray = JSON.parseArray(str);
        System.out.println(jsonArray.get(0));
        for (int i=0;i<jsonArray.size();i++){

            SeatInfo seatInfo1 = new SeatInfo((int)jsonArray.getJSONObject(i).get("seatinfo_row"),(int)jsonArray.getJSONObject(i).get("seatinfo_col"),(int)jsonArray.getJSONObject(i).get("seatinfo_parent"));
            seatInfoMapper.AddSeatInfo(seatInfo1);
        }

    }
}
