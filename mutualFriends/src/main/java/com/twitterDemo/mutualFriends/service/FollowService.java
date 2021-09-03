package com.twitterDemo.mutualFriends.service;

import java.util.List;
import java.util.Map;

public interface FollowService {
    public List<Map<String, Object>> getMutualFriends(String userNames) throws Exception;
}
