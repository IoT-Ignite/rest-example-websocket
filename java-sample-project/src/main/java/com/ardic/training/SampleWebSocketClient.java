package com.ardic.training;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import com.ardic.training.model.Subscribe;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ClientEndpoint
public class SampleWebSocketClient implements Runnable{
	private Session session;
	private Subscribe subscribe;
	private String uri;

	public SampleWebSocketClient(String uri, Subscribe subscribe) {
		this.subscribe = subscribe;
		this.uri = uri;
	}

	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		System.out.println("onOpen");
		ObjectMapper mapper = new ObjectMapper();
		try {
			sendMessage(mapper.writeValueAsString(subscribe));
		} catch (JsonProcessingException e) {
			System.out.println("Can not subscribe: " + e.getMessage());
		}
	}

	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(message);

	}

	public void onClose() {
		System.out.println("Closed");
	}

	public void sendMessage(String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException ex) {
			System.out.println("Error sending Message");
		}
	}

	@Override
	public void run() {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();

		try {
			container.connectToServer(this, new URI(this.uri));

		} catch (DeploymentException | IOException | URISyntaxException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("Test: Connect to Server " + session.getId());
		
	}

}
