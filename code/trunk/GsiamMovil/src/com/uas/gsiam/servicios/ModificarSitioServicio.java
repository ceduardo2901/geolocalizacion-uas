package com.uas.gsiam.servicios;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
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

		
		SitioDTO sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		requestEntity = new HttpEntity<SitioDTO>(sitio, requestHeaders);

		Intent intentModificarSitio = new Intent(
				Constantes.MODIFICAR_SITIO_FILTRO_ACTION);

		try {

			ResponseEntity<String> respuesta = restTemp.exchange(
					Constantes.MODIFICAR_SITIOS_SERVICE_URL, HttpMethod.PUT,
					requestEntity,String.class);
			
			if (respuesta.getStatusCode() == HttpStatus.OK) {
				intentModificarSitio.putExtra("respuesta", Constantes.MSG_MODIFICAR_SITIO_OK);
			} else {
				intentModificarSitio.putExtra("error", Constantes.MSG_ERROR_INESPERADO);

			}
			

		} catch (RestResponseException e) {
			
			Log.i(TAG, "Error: " + e.getMensaje());
			
			Log.e(TAG, "Error: " + e.getMensaje());
			intentModificarSitio.putExtra("error", e.getMensaje());
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentModificarSitio
					.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}catch(RestClientException e){
			Log.e(TAG, e.getMessage());
		}

		sendBroadcast(intentModificarSitio);

	}
}
