package com.uas.gsiam.servicios;

import org.apache.http.client.HttpClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;

public class CrearSitioServicio extends IntentService {

	protected static String TAG = "AgregarSitioServicio";
	private RestTemplate restTemp;
	private HttpEntity<SitioDTO> requestEntity;
	private HttpHeaders requestHeaders;

	public CrearSitioServicio() {
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
		Bundle bundle = intent.getExtras();

		SitioDTO sitio = (SitioDTO) bundle.getSerializable("sitio");

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		
		
		requestEntity = new HttpEntity<SitioDTO>(sitio,requestHeaders);
		intent.setAction(Constantes.CREAR_SITIO_FILTRO_ACTION);
		try {

//			ResponseEntity<String> respuesta = restTemp.postForObject(
//					Constantes.CREAR_SITIOS_SERVICE_URL, sitio,
//					ResponseEntity.class);


			 ResponseEntity<String> respuesta = restTemp.exchange(
			 Constantes.CREAR_SITIOS_SERVICE_URL, HttpMethod.POST, requestEntity,
			 String.class);
			// bundle.putString("respuesta", respuesta);

			if (respuesta.getStatusCode() == HttpStatus.OK) {
				intent.putExtra("respuesta", Constantes.MSG_CREAR_SITIO_OK);
			} else {
				intent.putExtra("error", Constantes.MSG_ERROR_INESPERADO);

			}

		} catch (RestResponseException e) {
			String msg = (String) e.getResponseEntity().getBody();
			Log.d(TAG, "Error: " + msg);
			intent.putExtra("error", msg);
		}

		sendBroadcast(intent);

	}

}
