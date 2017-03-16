package com.ardic.training;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

public class OAuth2 {

	public String getToken(String username, String password, String brand) {

		CloseableHttpClient client = null;
		String result = "";
		try {
			client = HttpClientBuilder.create().build();
			String basicAuth = "frontend:";
			if (!StringUtils.isEmpty(brand)) {
				basicAuth = brand + ":";
			}
			String usernamePassword = DatatypeConverter.printBase64Binary(basicAuth.getBytes());

			HttpPost tokenRequest = new HttpPost("https://api.ardich.com/api/v3/login/oauth");
			tokenRequest.addHeader("Content-Type", ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
			tokenRequest.setHeader("Authorization", "Basic " + usernamePassword);

			List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
			urlParameters.add(new BasicNameValuePair("grant_type", "password"));
			urlParameters.add(new BasicNameValuePair("username", username));
			urlParameters.add(new BasicNameValuePair("password", password));

			tokenRequest.setEntity(new UrlEncodedFormEntity(urlParameters));

			HttpResponse tokenResponse = client.execute(tokenRequest);
			result = IOUtils.toString(tokenResponse.getEntity().getContent());
			JSONObject json = new JSONObject(result);
			if (null == json.getString("access_token")) {
				throw new Exception("We could not get the token for user " + username);
			}

			String accessToken = json.getString("access_token");
			String refreshToken = json.getString("refresh_token");

			System.out.println("Access Token: " + accessToken);
			System.out.println("Refresh Token: " + refreshToken);

			return accessToken;
		} catch (Exception e) {
			System.out.println(e.getMessage() + " " + result);
		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		return "";
	}
}
