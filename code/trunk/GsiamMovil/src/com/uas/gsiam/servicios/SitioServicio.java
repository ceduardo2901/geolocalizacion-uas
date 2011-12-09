package com.uas.gsiam.servicios;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;

public class SitioServicio extends IntentService {

	protected static String TAG = "SitioServicio";
	private HttpEntity<SitioDTO> requestEntity;
	private HttpHeaders requestHeaders;
	protected RestTemplate restTemp;

	public SitioServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();
		requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory(
				HttpUtils.getNewHttpClient()));
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		SitioDTO sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		SitioDTO[] respuesta = null;
		
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		requestEntity = new HttpEntity<SitioDTO>(sitio, requestHeaders);
		
		Intent intentSitio = new Intent(Constantes.SITIO_FILTRO_ACTION);
		try {
			if (sitio.getLat() != null && sitio.getLon() != null) {
				Map<String, Double> parms = new HashMap<String, Double>();

				parms.put("lat", sitio.getLat());
				parms.put("lon", sitio.getLon());

				respuesta = restTemp.getForObject(
						Constantes.SITIOS_SERVICE_URL, SitioDTO[].class, parms);
			} else {
				Map<String, String> parms = new HashMap<String, String>();
				
				if(sitio.getIdSitio() != null){
					String id = String.valueOf(sitio.getIdSitio());
					parms.put("id", id);
				}else{
					parms.put("id", "0");
				}
				if(sitio.getNombre() != null){
					parms.put("nombre", sitio.getNombre());
				}else{
					parms.put("nombre", " ");
				}
 
				respuesta = restTemp.getForObject(
						Constantes.BUSQUEDA_SITIOS_SERVICE_URL,
						SitioDTO[].class, parms);
			}

			ArrayList<SitioDTO> lista = Util.getArrayListSitioDTO(respuesta);
			if(lista != null){
				for(SitioDTO s : lista){
					if(s.getPublicaciones() != null){
						List<PublicacionDTO> publicaciones = s.getPublicaciones();
						for(PublicacionDTO p : publicaciones){
							if(p.getFoto() != null){
								p.setFoto(comprimirFoto(p));
							}
						}
					}

				}
			}
			intentSitio.putExtra("sitios", lista);

			
			
		} catch (RestResponseException e) {
			
			Log.i(TAG, "Error: " + e.getMensaje());
			
			Log.e(TAG, "Error: " + e.getMensaje());
			intentSitio.putExtra("error", e.getMensaje());
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentSitio
					.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}
		
		sendBroadcast(intentSitio);

	}

	private byte[] comprimirFoto(PublicacionDTO pub){
		Bitmap myBitmap;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize=2;
		
		myBitmap = BitmapFactory.decodeByteArray(pub.getFoto(), 0, pub.getFoto().length, options);
		ByteArrayOutputStream out =  new ByteArrayOutputStream();
		myBitmap.compress(CompressFormat.JPEG, 60, out);
		return out.toByteArray();
				
	}
	

}
