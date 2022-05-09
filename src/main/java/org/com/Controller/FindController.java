package org.com.Controller;

import org.com.Entity.Find;
import org.com.Entity.Lost;
import org.com.Entity.QueryInfo;
import org.com.MyResponse.MyResponse;
import org.com.Service.Interface.FindService;
import org.com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/find")
public class FindController {

    @Autowired
    private FindService findService;

    @ResponseBody
    @RequestMapping("/getallfind")
    public MyResponse GetAllFind(@RequestBody QueryInfo queryInfo, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            List<Find> finds = findService.GetAllFind(queryInfo);
            int page =0 ;
            if (!finds.isEmpty()) {

                return new MyResponse("200", "查询成功", String.valueOf(finds.size()), finds, String.valueOf(page));
            } else return new MyResponse("201", "没有这些信息", "", null, "");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }



    @ResponseBody
    @RequestMapping("/uploadfindimg")
    public MyResponse UploadFindImg(@RequestParam("find_img") MultipartFile multipartFile, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            String imgPath = findService.UploadFindImg(multipartFile);
            if (!imgPath.equals("")) return new MyResponse("200","上传成功",imgPath,null,"");
            else  return new MyResponse("201","上传失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/addfind")
    public MyResponse AddFind(@RequestBody Find find, @RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            System.out.println(find.toString());
            int i = findService.AddFind(find);
            if (i!=0) return new MyResponse("200","添加失物消息成功","",null,"");
            else return new MyResponse("201","添加失物消息失败","",null,"");
        }else return new MyResponse("202","Jwt验证失败","",null,"");
    }

    @ResponseBody
    @RequestMapping("/updatestatus")
    public MyResponse UpdateStatus(@RequestParam int find_id,@RequestParam String find_status,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){
            findService.UpdateFindStatus(find_id,find_status);
            return new MyResponse("200","更新状态成功","",null,"");
        } return new MyResponse("201","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/deletefind")
    public MyResponse DeleteLost(@RequestParam int find_id,@RequestHeader("Authorization")String token){
        if (JwtUtil.VerifyToken(token)){

            findService.DeleteFind(find_id);
            return new MyResponse("200","删除成功","",null,"");
        }else   return new MyResponse("201","Jwt验证失败","",null,"");
    }
    @ResponseBody
    @RequestMapping("/getfindbytimerange")
    public MyResponse GetFindByTime(@RequestParam String start_time,@RequestParam String end_time,@RequestHeader("Authorization")String token) throws ParseException {
        if (JwtUtil.VerifyToken(token)){
            List<Find> findList = findService.GetFindByTime(start_time, end_time);

            return new MyResponse("200","查询成功",String.valueOf(findList.size()),findList,"");
        }else   return new MyResponse("201","Jwt验证失败","",null,"");
    }
}
