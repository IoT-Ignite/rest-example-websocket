package com.ardic.training.client;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.entity.ContentType;
import org.json.JSONException;

import com.ardic.training.exception.NotOKException;
import com.ardic.training.model.UserInfo;

public class GetUserInfo extends AbstractClient{
	private static final String SYSUSER_URL = "https://api.ardich.com/api/v3/sysuser/auditor";
	
	public void printUserInfo(String accessToken) {
		requestHeaders = new HashMap<String, String>();
		requestHeaders.put("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
		
		try {
			String result = getClient().get(SYSUSER_URL, requestHeaders, accessToken);
			
			UserInfo userInfo = getMapper().readValue(result, UserInfo.class);
			System.out.println("User: ");
			System.out.println(userInfo.toString());
		} catch (IllegalStateException | IOException | NotOKException | JSONException e) {
			System.out.println(e.getMessage());
		}
	}
}
