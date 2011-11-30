package com.uas.gsiam.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class HttpUtils {

	public static HttpClient getNewHttpClient() {
		try {
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams,
					Constantes.TIMEOUT);
			final HttpClient client = new DefaultHttpClient(httpParams);

			return client;
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}

}
