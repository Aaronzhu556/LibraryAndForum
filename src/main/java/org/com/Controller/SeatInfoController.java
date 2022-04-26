package org.com.Controller;

import org.com.Entity.Seat;
import org.com.Entity.SeatData;
import org.com.Entity.SeatInfo;
import org.com.Mapper.SeatMapper;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.SeatInfoService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

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
            seatInfoService.AddSeatInfo(seatInfo,JwtUtil.GetInformation(token));


            return new MyResponse("200","预定成功","",null,"");
        }else return new MyResponse("200","预定成功","",null,"");
    }


    @RequestMapping("/signin")
    @ResponseBody
    public MyResponse SigninSeat(@RequestBody SeatInfo seatInfo,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            int i = seatInfoService.SigninSeatInfo( seatInfo, JwtUtil.GetInformation(token));
            if (i !=0) return new MyResponse("200","签到成功","",null,"");
            else return new MyResponse("201","签到失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @RequestMapping("/signout")
    @ResponseBody
    public MyResponse SignoutSeat(@RequestParam int seatinfo_id,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            seatInfoService.SingoutSeatInfo(seatinfo_id);
            return new MyResponse("200","签退成功","",null,"");
        }else  return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @RequestMapping("/getseatinfobyuser")
    @ResponseBody
    public MyResponse GetSeatinfoByUser(@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<SeatInfo> seatInfoList = seatInfoService.GetSeatInfoByUser(JwtUtil.GetInformation(token));
            return new MyResponse("200","查询成功","",seatInfoList,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/getallseatinfo")
    public MyResponse GetAllSeatInfo(@RequestHeader("Authorization") String token) throws ParseException {
        if (JwtUtil.VerifyToken(token)){
            List<SeatData>  seatDataList = seatInfoService.GetAllSeatInfo();
            return new MyResponse("200","查询成功","",seatDataList,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }

}
