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

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;

/**
 * Servicio que crea un nuevo sitio de interes en el sistema. Se envian al
 * servidor el nombre, direccion, categoria y la ubicacion geografica.
 * Opcionalmente se pueden enviar el telefono y la url de la web del sitio
 * 
 * @author Antonio
 * 
 */
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

		SitioDTO sitio = (SitioDTO) intent.getSerializableExtra("sitio");
		ApplicationController app = ((ApplicationController) getApplicationContext());

		sitio.setIdUsuario(app.getUserLogin().getId());
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));

		requestEntity = new HttpEntity<SitioDTO>(sitio, requestHeaders);

		Intent intentBack = new Intent(Constantes.CREAR_SITIO_FILTRO_ACTION);
		try {

			
			ResponseEntity<String> respuesta = restTemp.exchange(
					Constantes.CREAR_SITIOS_SERVICE_URL, HttpMethod.POST,
					requestEntity, String.class);
			
			if (respuesta.getStatusCode() == HttpStatus.OK) {
				intentBack.putExtra("respuesta", Constantes.MSG_CREAR_SITIO_OK);
			} else {
				intentBack.putExtra("error", Constantes.MSG_ERROR_INESPERADO);

			}

		} catch (RestResponseException e) {
			String msg = e.getMensaje();
			Log.e(TAG, "Error: " + msg);
			intentBack.putExtra("error", msg);
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentBack.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}

		sendBroadcast(intentBack);

	}

}
