package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CrearSitioServicio extends IntentService {

	protected static String TAG = "AgregarSitioServicio";
	private RestTemplate restTemp;

	public CrearSitioServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();
		restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle bundle = intent.getExtras();

		SitioDTO sitio = (SitioDTO) bundle.getSerializable("sitio");

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		intent.setAction(Constantes.CREAR_SITIO_FILTRO_ACTION);
		try {

			// ResponseEntity<String> respuesta = restTemp.postForObject(
			// Constantes.CREAR_SITIOS_SERVICE_URL, sitio, String.class);
			ResponseEntity<String> respuesta = restTemp.exchange(
					Constantes.CREAR_SITIOS_SERVICE_URL, HttpMethod.POST, null,
					String.class);
			// bundle.putString("respuesta", respuesta);

			if (respuesta.getStatusCode() == HttpStatus.ACCEPTED) {
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
