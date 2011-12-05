package com.uas.gsiam.servicios;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;

public class ModificarSitioServicio extends IntentService {

	protected static String TAG = "ModificarSitioServicio";
	protected RestTemplate restTemp;
	private HttpEntity<SitioDTO> requestEntity;
	private HttpHeaders requestHeaders;

	public ModificarSitioServicio() {
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

		SitioDTO sitio = (SitioDTO) bundle.getParcelable("sitio");
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		Intent intentModificarSitio = new Intent(
				Constantes.MODIFICAR_SITIO_FILTRO_ACTION);
		try {

			restTemp.put(Constantes.MODIFICAR_SITIOS_SERVICE_URL, sitio);
			intentModificarSitio.putExtra("respuesta",
					Constantes.MSG_CREAR_SITIO_OK);

		} catch (RestResponseException e) {
			Log.i(TAG, "Error: " + e.getMessage());
			String msg = (String) e.getResponseEntity().getBody();
			Log.e(TAG, "Error: " + msg);
			intentModificarSitio.putExtra("error", msg);
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentModificarSitio
					.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}

		sendBroadcast(intentModificarSitio);

	}
}
