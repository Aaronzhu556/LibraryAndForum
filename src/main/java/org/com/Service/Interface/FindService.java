package org.com.Service.Interface;

import org.com.Entity.Find;
import org.com.Entity.QueryInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface FindService {
    public List<Find> GetAllFind(QueryInfo queryInfo);
    public String UploadFindImg(MultipartFile multipartFile);
    public  int AddFind(Find find);
    public int UpdateFindStatus(int find_id,String find_status);
}
