package com.twitterDemo.mutualFriends.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.twitterDemo.mutualFriends.utils.Utils;

@Service
public class FollowServiceImpl implements FollowService{
	
    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String, Object>> getMutualFriends(String userNames) throws Exception {
    	//Final List of Mutual Friends
    	List<Map<String,Object>> finalList = new ArrayList<Map<String,Object>>();
    	
    	//Get User details from user names
    	Map<String, Object> userDetails = Utils.getUserDetails(userNames);
    	
    	if(userDetails.get("data")!=null) {
    		
    		List<Map<String, Object>> userDetailsList = (List<Map<String, Object>>) userDetails.get("data");
    		
        	if(!userDetailsList.isEmpty() && userDetailsList.size()==2) {
        		String userId1 = (String) userDetailsList.get(0).get("id");
        		String userId2 = (String) userDetailsList.get(1).get("id");
        		
        		// Get Following of the users, Max 600 result are allowed in one Request 
        		Map<String, Object> firstUserFollowingResult = Utils.getFollowing(userId1,null);
        		Map<String, Object> secondUserFollowingResult = Utils.getFollowing(userId2,null);
        		
        		
        		List<Map<String, Object>> firstUserFollowingList = null;
        		if (firstUserFollowingResult!=null &&
        			firstUserFollowingResult.get("data")!=null &&
        			firstUserFollowingResult.get("data") instanceof List) {
        			
        			firstUserFollowingList = new ArrayList<Map<String,Object>>();
        			firstUserFollowingList.addAll((List<Map<String, Object>>) firstUserFollowingResult.get("data"));
        			Map<String,Object> meta = (Map<String, Object>) firstUserFollowingResult.get("meta");
        			String nextToken = meta!=null && meta.get("next_token")!=null? (String)meta.get("next_token"):null;
        			
        			while(nextToken!=null) {
        				
        				firstUserFollowingResult = Utils.getFollowing(userId1,nextToken);
        				firstUserFollowingList.addAll((List<Map<String, Object>>) firstUserFollowingResult.get("data"));
        				meta = (Map<String, Object>) firstUserFollowingResult.get("meta");
            			nextToken = meta!=null && meta.get("next_token")!=null? (String)meta.get("next_token"):null;
        			}
        		}
        		
        		List<Map<String, Object>> secondUserFollowingList = null;
        		if (secondUserFollowingResult!=null &&
        			secondUserFollowingResult.get("data")!=null &&
        			secondUserFollowingResult.get("data") instanceof List) {
        			
        			secondUserFollowingList = new ArrayList<Map<String,Object>>();
        			secondUserFollowingList.addAll((List<Map<String, Object>>) secondUserFollowingResult.get("data"));
        			Map<String,Object> meta = (Map<String, Object>) secondUserFollowingResult.get("meta");
        			String nextToken = meta!=null && meta.get("next_token")!=null? (String)meta.get("next_token"):null;
        			
        			while(nextToken!=null) {
        				
        				secondUserFollowingResult = Utils.getFollowing(userId2,nextToken);
        				secondUserFollowingList.addAll((List<Map<String, Object>>) secondUserFollowingResult.get("data"));
        				meta = (Map<String, Object>) secondUserFollowingResult.get("meta");
            			nextToken = meta!=null && meta.get("next_token")!=null? (String)meta.get("next_token"):null;
        			}
        			
        		}
        		
        		// Combine Followers and ollowing 
        		Map<String, Object> relatedToFirstUser = new HashMap<String, Object>();

        		if(firstUserFollowingList!=null) {
        			firstUserFollowingList.forEach(user->{
        				relatedToFirstUser.putIfAbsent((String) user.get("id"), user);
        			});
        		}
        		
        		Map<String, Object> relatedToSecondUser = new HashMap<String, Object>();

        		if(secondUserFollowingList!=null) {
        			secondUserFollowingList.forEach(user->{
        				relatedToSecondUser.putIfAbsent((String) user.get("id"), user);
        			});
        		}
        		
        		if (relatedToFirstUser.size()>relatedToSecondUser.size()) {
        			relatedToSecondUser.forEach((k,v)->{
        				if(relatedToFirstUser.get(k)!=null) {
        					finalList.add((Map<String, Object>) v);
        				}
        			});
        		}else {
        			relatedToFirstUser.forEach((k,v)->{
        				if(relatedToSecondUser.get(k)!=null) {
        					finalList.add((Map<String, Object>) v);
        				}
        			});
        		}
        		
        	}
        	
    	}else {
    		throw new Exception("User Details Not found!!");
    	}
    	
    	return finalList;
    }
}
