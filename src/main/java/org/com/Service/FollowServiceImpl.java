package org.com.Service;

import org.com.Mapper.FollowMapper;
import org.com.Service.Interface.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowServiceImpl implements FollowService {
    @Autowired
    private FollowMapper followMapper;

    @Override
    public List<String > GetUserFollowing(String follow_user_from){
        return followMapper.GetUserFollowing(follow_user_from);
    }
    @Override
    public List<String>GetUserFans(String follow_user_to){
        return followMapper.GetUserFans(follow_user_to);
    }
    @Override
    public int FollowUser(String follow_user_from,String follow_user_to){
        return followMapper.FollowUser(follow_user_from, follow_user_to);
    }

    @Override
    public int UnFollowUser(String follow_user_from,String follow_user_to){
        return followMapper.UnFollowUser(follow_user_from, follow_user_to);
    }
}
