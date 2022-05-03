package org.com.Service.Interface;

import org.com.Entity.Notice;
import org.com.Entity.QueryInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NoticeService {
    public List<Notice> GetAllNotice(QueryInfo queryInfo);
    public int DeleteNotice(int notice_id);
    public int AddNotice(Notice notice);
}
