package com.twitterDemo.mutualFriends.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.twitterDemo.mutualFriends.service.FollowService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/users")
public class UserController {

	@Autowired
	FollowService followService;
	
    @RequestMapping(value = "/mutualFriends", method = {RequestMethod.GET})
    public ResponseEntity<Map<String,Object>> mutualFriends(@RequestParam String userNames){
    	HttpStatus status = HttpStatus.OK;
    	Map<String,Object> response = null;
        try {
        	if(userNames.split(",").length !=2) {
        		throw new Exception("Exactly 2 user names are allowed!!");
        	}
        	List<Map<String,Object>>result = followService.getMutualFriends(userNames);
        	response = new HashMap<String, Object>();
        	response.put("data", result);
        	response.put("totalCount", result.size());
        }catch (Exception ex) {
        	response = new HashMap<String, Object>();
        	response.put("error", ex.getMessage());
        	status = HttpStatus.BAD_REQUEST;
        	ex.printStackTrace();
        }
        return new ResponseEntity<Map<String,Object>>(response, status);
    }
}
