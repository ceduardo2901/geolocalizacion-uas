package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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
 * Este servicio permite la eliminacion de un sitio de interes del sistema. Se
 * debera ingresar el identificador del sitio de interes a eliminar
 * 
 * @author Antonio
 * 
 */
public class EliminarSitioServicio extends IntentService {

	protected static String TAG = "EliminarSitioServicio";

	protected RestTemplate restTemp;
	private HttpEntity<SitioDTO> requestEntity;
	private HttpHeaders requestHeaders;

	public EliminarSitioServicio() {
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

		Integer sitio = intent.getIntExtra("sitio", 0);
		ApplicationController app = ((ApplicationController) getApplicationContext());
		Map<String, Integer> parms = new HashMap<String, Integer>();
		
		parms.put("sitio", sitio);
		parms.put("usuario", app.getUserLogin().getId());
		
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		
		
		Intent intentEliminarSitio = new Intent(
				Constantes.ELIMINAR_SITIO_FILTRO_ACTION);
		try {

			restTemp.delete(Constantes.ELIMINAR_SITIOS_SERVICE_URL, parms);

			intentEliminarSitio.putExtra("respuesta",
					Constantes.MSG_ELIMINAR_SITIO_OK);

		} catch (RestResponseException e) {
			String msg = e.getMensaje();
			Log.e(TAG, "Error: " + msg);
			intentEliminarSitio.putExtra("error", msg);

		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentEliminarSitio.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}
		sendBroadcast(intentEliminarSitio);

	}
}
