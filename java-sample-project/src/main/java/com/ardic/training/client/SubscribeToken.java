package com.ardic.training.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import com.ardic.training.exception.NotOKException;
import com.ardic.training.model.TokenResponse;

public class SubscribeToken extends AbstractClient{
	private static final String SUBSCRIBE_URL = "https://api.ardich.com/api/v3/subscribe/device/token?device=";
	public TokenResponse getSubscribeToken(String deviceId, String accessToken) {
		String requestUrl = SUBSCRIBE_URL + deviceId;
		Map<String, String> requestHeaders = new HashMap<String, String>();
		TokenResponse response = null;
		try {
			String result = getClient().get(requestUrl, requestHeaders, accessToken);
			response = getMapper().readValue(result, TokenResponse.class);
			System.out.println("Subscribe token:");
			System.out.println(response.toString());
		} catch (IllegalStateException | IOException | NotOKException | JSONException e) {
			System.out.println(e.getMessage());
		}
		
		return response;
	}
}
