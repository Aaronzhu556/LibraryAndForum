package org.com.Controller;

import org.com.Entity.Lost;
import org.com.Entity.QueryInfo;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.LostService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/lost")
public class LostController {
    @Autowired
    private LostService lostService;

    @ResponseBody
    @RequestMapping("/getalllost")
    public MyResponse GetAllLost(@RequestBody QueryInfo queryInfo, @RequestHeader("Authorization")String token) {
        if (JwtUtil.VerifyToken(token)) {
            List<Lost> losts = lostService.GetAllLost(queryInfo);
            List<Lost> lostList = new LinkedList<>();
            int page = 0;
            if (!losts.isEmpty()) {

                int count = 0;
                if (!queryInfo.getQuerydata().equals(queryInfo.getQuerytext()) && queryInfo.getPagenum() != 1) { // 说明这是第一次查询
                    for (int i = 0; count < queryInfo.getPagesize(); i++) {
                        try {
                            lostList.add(losts.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    page = 1;
                } else {
                    for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                        try {

                            lostList.add(losts.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
                return new MyResponse("200", "查询成功", String.valueOf(losts.size()), lostList, String.valueOf(page));
            } else return new MyResponse("201", "没有这些信息", "", null, "");
        } else return new MyResponse("202", "Jwt验证失败", "", null, "");
    }
    @ResponseBody
    @RequestMapping("/uploadlostimg")
    public MyResponse UploadLostImg(@RequestParam("lost_img") MultipartFile multipartFile,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            String imgPath = lostService.UploadLostImg(multipartFile);
            if (!imgPath.equals("")) return new MyResponse("200","上传成功",imgPath,null,"");
            else  return new MyResponse("201","上传失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/addlost")
    public MyResponse AddLost(@RequestBody Lost lost,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            System.out.println(lost.toString());
            int i = lostService.AddLost(lost);
            if (i!=0) return new MyResponse("200","添加拾物消息成功","",null,"");
            else return new MyResponse("201","添加拾物消息失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/updatestatus")
    public MyResponse UpdateStatus(@RequestParam int lost_id,@RequestParam String lost_status,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            lostService.UpdateLostStatus(lost_id,lost_status);
            return new MyResponse("200","更新状态成功","",null,"");
        }else return new MyResponse("201","Jwt验证失败","",null,"");
    }
}
