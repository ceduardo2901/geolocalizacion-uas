package com.uas.gsiam.utils;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

/**
 * 
 * Clase encargada de crear nuevo cliente http para la ejecucion de servicios
 * web
 * 
 * @author Antonio
 * 
 */
public class HttpUtils {

	/**
	 * Metodo que crea un cliente http y se le setea el tiempo de timeout que el
	 * request se queda escuchando por una respuesta
	 * 
	 * @return Retorna un nuevo cliente http
	 */
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
