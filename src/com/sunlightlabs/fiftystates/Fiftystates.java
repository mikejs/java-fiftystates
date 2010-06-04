package com.sunlightlabs.fiftystates;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Fiftystates {
	public static String userAgent = "java-fiftystates 0.1";
	public static String apiKey = "";
	public static String baseUrl = "http://fiftystates-dev.sunlightlabs.com/api/";
	
	public static String url(String method, String queryString) {
		if (queryString.length() > 0) {
			queryString += "&";
		}
		
		queryString += "apikey=" + apiKey;
		queryString += "&format=json"; 
 		return baseUrl + method + "?" + queryString;
	}
	
	public static String fetchJSON(String url) {
		HttpGet request = new HttpGet(url);
		request.addHeader("User-Agent", userAgent);
		DefaultHttpClient client = new DefaultHttpClient();
		
		try {
			HttpResponse response = client.execute(request);
			int statusCode = response.getStatusLine().getStatusCode();
			
			if (statusCode == HttpStatus.SC_OK) {
				String body = EntityUtils.toString(response.getEntity());
				return body;
			} else {
				return "";
			}
		} catch (Exception e) {
			return "";
		}
	}
}
