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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        find.setFind_time(sdFormat.format(date));
        return findMapper.AddFind(find);
    }
    @Override
    public int UpdateFindStatus(int find_id,String find_status){
        return findMapper.UpdateFindStatus(find_status,find_id);
    }
}
