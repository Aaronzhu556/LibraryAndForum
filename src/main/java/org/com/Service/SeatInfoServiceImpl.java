package org.com.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.com.Entity.SeatData;
import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatInfoMapper;
import org.com.Redis.RedisServer;
import org.com.Service.Interface.SeatInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SeatInfoServiceImpl implements SeatInfoService {
    @Autowired
    private SeatInfoMapper seatInfoMapper;
    @Autowired
    private RedisServer redisServer;
    @Override
    public List<SeatInfo> QuerySeatInfo(int seat_id){
        return seatInfoMapper.QuerySeatInfo(seat_id);
    }

    @Override
    public void AddSeatInfo(String seatInfo,String user_name){
        List<SeatInfo> seatInfoList = new LinkedList<>();
        JSONObject jsonObject = JSON.parseObject(seatInfo);
        String str = jsonObject.getString("seatInfo_list");
        JSONArray jsonArray = JSON.parseArray(str);
        System.out.println(jsonArray.get(0));


        for (int i=0;i<jsonArray.size();i++){
            Date date = new Date();
            SeatInfo seatInfo1 = new SeatInfo((int)jsonArray.getJSONObject(i).get("seatinfo_row"),(int)jsonArray.getJSONObject(i).get("seatinfo_col"),(int)jsonArray.getJSONObject(i).get("seatinfo_parent"),user_name ,0);
            List<String> list = new LinkedList<>();
            list.add(String.valueOf(seatInfo1.getSeatinfo_row()));
            list.add(String.valueOf(seatInfo1.getSeatinfo_col()));
            list.add(String.valueOf(seatInfo1.getSeatinfo_parent()));
            String key_str = String.join(",",list);
            redisServer.putValue(key_str,user_name);//把数据加入redis
            System.out.println(redisServer.getAllKeys());
            System.out.println(redisServer.getValue(key_str));
            seatInfoMapper.AddSeatInfo(seatInfo1);
        }

    }

    @Override
    public int SigninSeatInfo(SeatInfo seatInfo, String user_name){//签到
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=  new Date();
        System.out.println(seatInfo.toString());
        List<String> list1 = new LinkedList<>();
        list1.add(String.valueOf(seatInfo.getSeatinfo_row()));
        list1.add(String.valueOf(seatInfo.getSeatinfo_col()));
        list1.add(String.valueOf(seatInfo.getSeatinfo_parent()));
        String key_str = String.join(",",list1);
        System.out.println(redisServer.getValue(key_str));
        Boolean aBoolean = redisServer.deleteKey(key_str);//把数据从redis中删除
        System.out.println(redisServer.getValue(key_str));
        System.out.println(aBoolean);
        seatInfoMapper.UpdateSeatInfoTime(sdFormat.format(date),seatInfo.getSeatinfo_id());
        return seatInfoMapper.UpdateSeatinfoStatus(seatInfo.getSeatinfo_id(),1);
    }

    @Override
    public List<SeatInfo>GetSeatInfoByUser(String user_name){
        return seatInfoMapper.GetSeatInfoByUser(user_name);
    }

    @Override
    public int SingoutSeatInfo(int seatinfo_id){//签退
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date=new Date();
        seatInfoMapper.UpdateSeatInfoIsLeft(1,seatinfo_id);
        return seatInfoMapper.UpdateSeatInfoTimeEnd(sdFormat.format(date),seatinfo_id);

    }

    @Override
    public List<SeatData> GetAllSeatInfo() throws ParseException {
        List<SeatData> seatDataList = new LinkedList<>();
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
            seatDataList.add(new SeatData(sdFormat.format(date_temp),0));
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
                    seatDataList.get(i).setSeatinfo_num(integer_temp);
                }

            }
        }
        System.out.println(map.toString());
        return seatDataList;

    }
}
