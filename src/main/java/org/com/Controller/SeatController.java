package org.com.Controller;

import org.com.Entity.Seat;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.SeatService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/seat")
public class SeatController {
    @Autowired
    private SeatService seatService;
    @RequestMapping("getallseat")
    @ResponseBody
    public MyResponse GetAllSeat(@RequestHeader("Authorization") String token){
        if (JwtUtil.VerifyToken(token)){
            List<Seat> seats = seatService.GetAllSeat();
            return new MyResponse("200","查询成功","",seats,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }
}
