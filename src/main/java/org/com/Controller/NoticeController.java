package org.com.Controller;

import io.jsonwebtoken.Jwt;
import org.com.Entity.Notice;
import org.com.Entity.QueryInfo;
import org.com.Entity.Seat;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.NoticeService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/notice")
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @RequestMapping("/getallnotice")
    @ResponseBody
    public MyResponse GetAllNotice(@RequestBody QueryInfo queryInfo, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Notice> notices = noticeService.GetAllNotice(queryInfo);
            List<Notice> noticeList = new LinkedList<>();
            int count=0;
            for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                try {

                    noticeList.add(notices.get(i));
                    count++;
                } catch (Exception e) {
                    break;
                }
            }
            return new MyResponse("200","查询成功",String.valueOf(notices.size()),noticeList,"");
        }
        else  return new MyResponse("201","Jwt验证失败","",null,"");
    }
    @RequestMapping("/deletenotice")
    @ResponseBody
    public MyResponse DeleteNotice(@RequestParam int notice_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            noticeService.DeleteNotice(notice_id);
            return new MyResponse("200","删除成功","",null,"");
        }else  return new MyResponse("201","Jwt验证成功","",null,"");
    }

    @RequestMapping("/addnotice")
    @ResponseBody
    public MyResponse AddNotice(@RequestBody Notice notice,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            noticeService.AddNotice(notice);
            return new MyResponse("200","添加成功","",null,"");
        }else  return new MyResponse("201","Jwt验证成功","",null,"");
    }
}
