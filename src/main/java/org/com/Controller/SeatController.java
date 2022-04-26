package org.com.Controller;

import org.com.Entity.Book;
import org.com.Entity.QueryInfo;
import org.com.Entity.Seat;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.SeatService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @RequestMapping("/getallseat")
    @ResponseBody
    public MyResponse GetAllSeat(@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Seat> seats = seatService.GetAllSeat();

            return new MyResponse("200","查询成功","",seats,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }


    @RequestMapping("/getallseatbyquery")
    @ResponseBody
    public MyResponse GetAllSeatByQuery(@RequestBody QueryInfo queryInfo,@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Seat> seats = seatService.GetAllSeatByQuery();
            List<Seat> seatList = new LinkedList<>();
            int count=0;
            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                try {

                    seatList.add(seats.get(i));
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            if (!seatList.isEmpty())return new MyResponse("200","查询成功",String.valueOf(seats.size()),seatList,"");
            else return new MyResponse("201","查询失败","",null,"");
        }else  return new MyResponse("202","Jwt验证失败","",null,"");

    }
    @ResponseBody
    @RequestMapping("/deleteseat")
    public MyResponse DeleteSeat(int seat_id, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)) {
            int i = seatService.DeleteSeat(seat_id);
            if (i != 0) return new MyResponse("200", "删除成功", "", null, "");
            else return new MyResponse("201", "删除失败", "", null, "");
        }else return new MyResponse("202", "Jwt验证失败", "", null, "");
    }
    @ResponseBody
    @RequestMapping("/addseat")
    public MyResponse AddSeat(@RequestBody Seat seat,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            int i = seatService.AddSeat(seat);
            if (i!=0)return new MyResponse("200","自习室添加成功","",null,"");
            else return new MyResponse("201","自习室添加失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
}
