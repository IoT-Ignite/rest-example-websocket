package com.ardic.training;

import com.ardic.training.model.Subscribe;
import com.ardic.training.model.SubscribeMessage;
import com.ardic.training.model.TokenResponse;

public class WebsocketImpl implements Runnable {
	private TokenResponse tokenResponse;
	private String deviceId;
	
	public WebsocketImpl(TokenResponse tokenResponse, String deviceId) {
		this.tokenResponse = tokenResponse;
		this.deviceId = deviceId;
	}

	public Subscribe generateSubscribeMessage() {
		SubscribeMessage subscribeMessage = new SubscribeMessage();
		subscribeMessage.setDeviceId(deviceId);
		subscribeMessage.setNodeId("");
		subscribeMessage.setSensorId("");
		subscribeMessage.setSubscribeId(tokenResponse.getId());
		subscribeMessage.setToken(tokenResponse.getToken());
		subscribeMessage.setVersion("2.0");

		Subscribe subscribe = new Subscribe();
		subscribe.setMessage(subscribeMessage);
		subscribe.setType("subscribe");

		return subscribe;
	}
	
	private void websocket(String url, Subscribe subscribe) {
		SampleWebSocketClient client = new SampleWebSocketClient(url, subscribe);
		client.run();
	}

	@Override
	public void run() {
		Subscribe subscribe = generateSubscribeMessage();
		websocket(tokenResponse.getUrl(), subscribe);
	}
}
