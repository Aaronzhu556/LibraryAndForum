package org.com.Service;

import org.com.Entity.Notice;
import org.com.Entity.QueryInfo;
import org.com.Mapper.NoticeMapper;
import org.com.Service.Interface.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public List<Notice> GetAllNotice(QueryInfo queryInfo){
        if (queryInfo.getQuerytext().equals("")) return noticeMapper.GetAllNotice();
        else return noticeMapper.GetNoticeByName(queryInfo.getQuerytext());
    }
    @Override
    public int DeleteNotice(int notice_id){
        return noticeMapper.DeleteNotice(notice_id);
    }

    @Override
    public int AddNotice(Notice notice){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        notice.setNotice_time(simpleDateFormat.format(date));
        return noticeMapper.AddNotice(notice);
    }
}
