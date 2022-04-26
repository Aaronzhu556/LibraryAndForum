package org.com.Service.Interface;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FollowService {
    public List<String >GetUserFollowing(String follow_user_from);
    public List<String>GetUserFans(String follow_user_to);
    public int FollowUser(String follow_user_from,String follow_user_to);
    public int UnFollowUser(String follow_user_from,String follow_user_to);
}
