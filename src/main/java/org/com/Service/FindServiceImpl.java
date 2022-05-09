package org.com.Service;

import org.com.Entity.Find;
import org.com.Entity.Find;
import org.com.Entity.QueryInfo;
import org.com.Mapper.FindMapper;
import org.com.Mapper.FindMapper;
import org.com.Service.Interface.FindService;
import org.com.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class FindServiceImpl implements FindService {
    @Autowired
    private FindMapper findMapper;

    @Value("D:/graduateproject2photo/")
    private String imgPath;
    @Override
    public List<Find> GetAllFind(QueryInfo queryInfo){
        if (queryInfo.getQuerytext().equals("")){

            List<Find> findList = findMapper.GetAllFind();
            for (Find find:findList){
                List<String>list = Arrays.asList(find.getFind_img().split(","));
                find.setFind_img_list(list);
            }
            return findList;
        }else {


            List<Find> findList = findMapper.GetFindByName(queryInfo.getQuerytext());
            for (Find find:findList){
                List<String>list = Arrays.asList(find.getFind_img().split(","));
                find.setFind_img_list(list);
            }
            return findList;
        }
    }
    @Override
    public String UploadFindImg(MultipartFile multipartFile){
        return ImageUtil.UploadPhoto(multipartFile,imgPath,"");
    }
    @Override
    public  int AddFind(Find find){
        find.setFind_img(String.join(",",find.getFind_img_list()));

        return findMapper.AddFind(find);
    }
    @Override
    public int UpdateFindStatus(int find_id,String find_status){
        return findMapper.UpdateFindStatus(find_status,find_id);
    }
    @Override
    public int DeleteFind(int find_id){
        return findMapper.DeleteFind(find_id);
    }

    @Override
    public List<Find> GetFindByTime(String start_time,String end_time) throws ParseException {
        List<Find> findList = findMapper.GetAllFind();
        List<Find> finds = new LinkedList<>();
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date_start = sdFormat.parse(start_time);
        Date date_end = sdFormat.parse(end_time);
        for (Find find:findList){
            Date date = sdFormat.parse(find.getFind_time());
            if (date.after(date_start)&&date.before(date_end)) {
                List<String>list = Arrays.asList(find.getFind_img().split(","));
                find.setFind_img_list(list);
                finds.add(find);
            }
        }
        return finds;
    }

}
