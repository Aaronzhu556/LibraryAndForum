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
            List<Find> findList = new LinkedList<>();
            int page =0 ;
            if (!finds.isEmpty()) {

                int count = 0;
                if (!queryInfo.getQuerydata().equals(queryInfo.getQuerytext()) && queryInfo.getPagenum() != 1) { // 说明这是第一次查询
                    for (int i = 0; count < queryInfo.getPagesize(); i++) {
                        try {
                            findList.add(finds.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                    page = 1;
                } else {
                    for (int i = ((queryInfo.getPagenum() - 1) * queryInfo.getPagesize()); count < queryInfo.getPagesize(); i++) {
                        try {

                            findList.add(finds.get(i));
                            count++;
                        } catch (Exception e) {
                            break;
                        }
                    }
                }
                return new MyResponse("200", "查询成功", String.valueOf(finds.size()), findList, String.valueOf(page));
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
}
