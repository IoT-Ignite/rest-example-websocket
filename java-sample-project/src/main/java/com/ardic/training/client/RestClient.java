package com.ardic.training.client;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;

import com.ardic.training.exception.NotOKException;

public class RestClient {
	public String get(String requestUrl, Map<String, String> requestHeaders, String accessToken) throws IllegalStateException, IOException, NotOKException, JSONException {
		CloseableHttpClient client = null;
		String result = StringUtils.EMPTY;
		try {
			client = HttpClientBuilder.create().build();

			HttpGet request = new HttpGet(requestUrl);
			for (Entry<String, String> requestHeader : requestHeaders.entrySet()) {
				request.addHeader(requestHeader.getKey(), requestHeader.getValue());
			}
			
			request.setHeader("Authorization", "Bearer " + accessToken);

			HttpResponse response = client.execute(request);
			
			if(null == response || null == response.getStatusLine() || response.getStatusLine().getStatusCode() != 200) {
				throw new NotOKException(IOUtils.toString(response.getEntity().getContent()));
			}
			
			result = IOUtils.toString(response.getEntity().getContent());
			
			

		} finally {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
		
		return result;
	}
}
