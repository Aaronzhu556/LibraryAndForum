package org.com.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.com.Entity.Seat;
import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatMapper;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.SeatInfoService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.json.Json;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/seatinfo")
public class SeatInfoController {
    @Autowired
    private SeatInfoService seatInfoService;
    @Autowired
    private SeatMapper seatMapper;
    @RequestMapping("/searchallseatinfo")
    @ResponseBody
    public MyResponse QueryAllSeatInfo(@RequestParam int seat_id,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<SeatInfo> seatInfos = seatInfoService.QuerySeatInfo(seat_id);
            Seat seat = seatMapper.QuerySeatBUId(seat_id);
            seat.setSeat_info(seatInfos);
            System.out.println(seat.getSeat_info());
            return new MyResponse("200","查询成功","",seat,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @RequestMapping("/addseatinfo")
    @ResponseBody
    public MyResponse AddSeatInfo(@RequestBody String seatInfo,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            seatInfoService.AddSeatInfo(seatInfo);


            return new MyResponse("200","预定成功","",null,"");
        }else return new MyResponse("200","预定成功","",null,"");
    }
}
