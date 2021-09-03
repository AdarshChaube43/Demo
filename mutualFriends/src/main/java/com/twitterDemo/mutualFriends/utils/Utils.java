package com.twitterDemo.mutualFriends.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Utils {
    private static final String accessToken = "AAAAAAAAAAAAAAAAAAAAAHI8TQEAAAAAXB5bwfIo39e4LvmHzxx8zK7T5%2Bw%3DyYIX52HkJTEIMoc3i6zBGKzFOFBqDmhGMhpsjLYHv80KxiJAg0";

    public static Map<String, Object> getUserDetails(String userNames) throws IOException, ParseException {
    	Map<String, Object> response = null;
        URL url = new URL("https://api.twitter.com/2/users/by?usernames="+userNames);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        conn.setRequestProperty("Authorization","Bearer "+accessToken);
        int status = conn.getResponseCode();
        
        if(status == 200) {
        	 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             JSONParser parser = new JSONParser();
             response = (Map<String, Object>) parser.parse(br.readLine());
        }
        
        return response;
    }

    public static Map<String, Object> getFollowers(String userId, String pagination_token) throws IOException, ParseException{
    	Map<String, Object> response = null;
    	StringBuilder urlStr = new StringBuilder("https://api.twitter.com/2/users/"+userId+"/followers");
    	urlStr.append("?max_results=600");
    	if(pagination_token!=null && !pagination_token.isEmpty()) {
    		urlStr.append("?pagination_token="+pagination_token);
    	}
        URL url = new URL(urlStr.toString());
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(7000);
        conn.setReadTimeout(7000);
        conn.setRequestProperty("Authorization","Bearer "+accessToken);
        int status = conn.getResponseCode();
        
        if(status == 200) {
        	 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             JSONParser parser = new JSONParser();
             response = (Map<String, Object>) parser.parse(br.readLine());
        }
        
        return response;
    }

    public static Map<String, Object> getFollowing(String userId, String pagination_token) throws IOException, ParseException{
    	Map<String, Object> response = null;
    	StringBuilder urlStr = new StringBuilder("https://api.twitter.com/2/users/"+userId+"/following");
    	urlStr.append("?max_results=600");
    	if(pagination_token!=null && !pagination_token.isEmpty()) {
    		urlStr.append("&pagination_token="+pagination_token);
    	}
    	System.out.println(urlStr.toString());
        URL url = new URL(urlStr.toString());
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(7000);
        conn.setReadTimeout(7000);
        conn.setRequestProperty("Authorization","Bearer "+accessToken);
        int status = conn.getResponseCode();
        
        if(status == 200) {
        	 BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
             JSONParser parser = new JSONParser();
             response = (Map<String, Object>) parser.parse(br.readLine());
        }
        
        return response;
    }





}
