package com.uas.gsiam.servicios;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;

/**
 * 
 * Servicio que responde a una solicitud de amistad pendiente.
 * 
 * @author Martín
 * 
 */
public class ResponderSolicitudServicio extends IntentService {

	protected static String TAG = "ResponderSolicitudServicio";
	protected RestTemplate restTemp;
	private HttpEntity<SolicitudContactoDTO> requestEntity;
	private HttpHeaders requestHeaders;

	public ResponderSolicitudServicio() {
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

		String accion = intent.getStringExtra("accion");
		SolicitudContactoDTO solicitud = (SolicitudContactoDTO) intent
				.getSerializableExtra("solicitud");

		requestEntity = new HttpEntity<SolicitudContactoDTO>(solicitud,
				requestHeaders);
		Intent intentBack = new Intent(
				Constantes.RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION);

		try {

			restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
					String.class));
			ResponseEntity<String> respuesta;
			if (accion.equalsIgnoreCase(Constantes.ACEPTAR_SOLICITUD)) {

				respuesta = restTemp.exchange(
						Constantes.ACEPTAR_SOLICITUD_AMISTAD_SERVICE_URL,
						HttpMethod.POST, requestEntity, String.class);
			} else {

				respuesta = restTemp.exchange(
						Constantes.RECHAZAR_SOLICITUD_AMISTAD_SERVICE_URL,
						HttpMethod.POST, requestEntity, String.class);
			}

			if (respuesta.getStatusCode() == HttpStatus.OK) {
				
				if (accion.equalsIgnoreCase(Constantes.ACEPTAR_SOLICITUD)) {
					intentBack.putExtra("respuesta", Constantes.MSG_SOLICITUD_APROBADA);
				}
				else{
					intentBack.putExtra("respuesta", Constantes.MSG_SOLICITUD_RECHAZADA);
				}
				
			} else {
				intentBack.putExtra("error", Constantes.MSG_ERROR_INESPERADO);

			}

		} catch (RestResponseException e) {
			String msg = e.getMensaje();
			intentBack.putExtra("error", msg);
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentBack.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);
		}

		sendBroadcast(intentBack);

	}

}
