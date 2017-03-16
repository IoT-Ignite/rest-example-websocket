package com.ardic.training.client;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AbstractClient {
	private final RestClient client;
	protected Map<String, String> requestHeaders;
	private final ObjectMapper mapper;

	public AbstractClient() {
		client = new RestClient();
		mapper = new ObjectMapper();
	}

	public RestClient getClient() {
		return client;
	}

	public Map<String, String> getRequestHeaders() {
		return requestHeaders;
	}

	public void setRequestHeaders(Map<String, String> requestHeaders) {
		this.requestHeaders = requestHeaders;
	}

	public ObjectMapper getMapper() {
		return mapper;
	}

}
