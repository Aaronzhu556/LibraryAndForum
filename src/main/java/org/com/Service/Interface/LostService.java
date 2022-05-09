package org.com.Service.Interface;

import org.com.Entity.Lost;
import org.com.Entity.QueryInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;

@Service
public interface LostService {
    public List<Lost> GetAllLost(QueryInfo queryInfo);
    public String UploadLostImg(MultipartFile multipartFile);
    public  int AddLost(Lost lost);
    public  int UpdateLostStatus(int lost_id,String lost_status);
    public int DeleteLost(int lost_id);
    public List<Lost> GetLostByTime(String start_time, String end_time) throws ParseException;
}
