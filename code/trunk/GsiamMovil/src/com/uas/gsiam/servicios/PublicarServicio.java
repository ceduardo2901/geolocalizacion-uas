package com.uas.gsiam.servicios;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;

/**
 * 
 * Servicio que crea una nueva publicacion para un sitio de interes. Se puede
 * publicar un comentario, puntaje y una foto del sitio, ademas es posible
 * compartir esta informacion en tu muro de facebook
 * 
 * @author Antonio
 * 
 */
public class PublicarServicio extends IntentService {

	protected static String TAG = "PublicarServicio";
	protected RestTemplate restTemp;
	private HttpEntity<PublicacionDTO> requestEntity;
	private HttpHeaders requestHeaders;

	public PublicarServicio() {
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

		PublicacionDTO publicacion = (PublicacionDTO) intent
				.getSerializableExtra("publicacion");

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));

		requestEntity = new HttpEntity<PublicacionDTO>(publicacion, requestHeaders);

		Intent intentPublicacion = new Intent(
				Constantes.CREAR_PUBLICACION_FILTRO_ACTION);

		try {
			
		
			ResponseEntity<Integer> respuesta = restTemp.exchange(
					Constantes.CREAR_PUBLICACION_SERVICE_URL, HttpMethod.POST,
					requestEntity, Integer.class);
			

			
			intentPublicacion.putExtra("respuesta", respuesta.getBody().intValue());
			
		} catch (RestResponseException e) {
			String msg = e.getMensaje();
			Log.e(TAG, "Error: " + msg);
			intentPublicacion.putExtra("error", msg);

		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentPublicacion.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}

		sendBroadcast(intentPublicacion);

	}

}
