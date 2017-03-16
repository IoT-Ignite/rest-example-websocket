package com.ardic.training;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.ardic.training.client.GetUserInfo;
import com.ardic.training.client.SubscribeToken;
import com.ardic.training.model.TokenResponse;

public class SampleRestCall {
	
	private static final String MAIL = "demo11@makerhane.com";
	private static final String PASSWORD = "a12345";
	private static final String DEVICE_ID = "b8:27:eb:a2:20:05@iotigniteagent"; 

	public static void main(String[] args) {
		OAuth2 oAuth2 = new OAuth2();
		String accessToken = oAuth2.getToken(MAIL, PASSWORD, null);

		GetUserInfo userInfo = new GetUserInfo();
		userInfo.printUserInfo(accessToken);
		
		SubscribeToken token = new SubscribeToken();
		TokenResponse tokenResponse = token.getSubscribeToken(DEVICE_ID, accessToken);

		WebsocketImpl impl = new WebsocketImpl(tokenResponse, DEVICE_ID);
		impl.run();
		
		waitForExit();
	}
	
	private static void waitForExit() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			do {
				input = br.readLine();
			} while (!input.equals("exit"));

		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(-1);
		}

		System.out.println("Exiting...");
		System.exit(0);
	}

}
