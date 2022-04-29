package org.com.Service;

import org.com.Entity.Lost;
import org.com.Entity.QueryInfo;
import org.com.Mapper.LostMapper;
import org.com.Service.Interface.LostService;
import org.com.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class LostServiceImpl implements LostService {
    @Autowired
    private LostMapper lostMapper;

    @Value("D:/graduateproject2photo/")
    private String imgPath;
    @Override
    public List<Lost> GetAllLost(QueryInfo queryInfo){
      if (queryInfo.getQuerytext().equals("")){

          List<Lost> lostList = lostMapper.GetAllLost();
          for (Lost lost:lostList){
              List<String>list = Arrays.asList(lost.getLost_img().split(","));
              lost.setLost_img_list(list);
          }
          return lostList;
      }else {


          List<Lost> lostList = lostMapper.GetLostByName(queryInfo.getQuerytext());
          for (Lost lost:lostList){
              List<String>list = Arrays.asList(lost.getLost_img().split(","));
              lost.setLost_img_list(list);
          }
          return lostList;
      }
    }

    @Override
    public String UploadLostImg(MultipartFile multipartFile){
        return ImageUtil.UploadPhoto(multipartFile,imgPath,"");
    }

    @Override
    public  int AddLost(Lost lost){
        lost.setLost_img(String.join(",",lost.getLost_img_list()));
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        lost.setLost_time(sdFormat.format(date));
        return lostMapper.AddLost(lost);
    }
    @Override
    public  int UpdateLostStatus(int lost_id,String lost_status){
        return lostMapper.UpdateLostStatus(lost_status,lost_id);
    }
}
