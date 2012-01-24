package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;

/**
 * Servicio que recupera las solicitudes pendientes para el usario ingresado.
 * 
 * @author Martín
 * 
 */
public class GetSolicitudesPendientesServicio extends IntentService {

	protected static String TAG = "GetSolicitudesPendientesServicio";
	protected RestTemplate restTemp;
	protected HttpHeaders requestHeaders;

	public GetSolicitudesPendientesServicio() {
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

		ApplicationController app = ((ApplicationController) getApplicationContext());
		UsuarioDTO user = app.getUserLogin();

		Map<String, Integer> parms = new HashMap<String, Integer>();
		parms.put("id", user.getId());

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		Intent intentBack = new Intent(Constantes.GET_SOLICITUDES_FILTRO_ACTION);

		try {

			UsuarioDTO[] respuesta = restTemp.getForObject(
					Constantes.GET_SOLICITUDES_RECIBIDAS_SERVICE_URL,
					UsuarioDTO[].class, parms);

			intentBack.putExtra("listaRecibidas",
					Util.getArrayListUsuarioDTO(respuesta));

			respuesta = restTemp.getForObject(
					Constantes.GET_SOLICITUDES_ENVIADAS_SERVICE_URL,
					UsuarioDTO[].class, parms);

			intentBack.putExtra("listaEnviadas",
					Util.getArrayListUsuarioDTO(respuesta));

		} catch (RestResponseException e) {
			String msg = e.getMensaje();
			intentBack.putExtra("error", msg);
		} catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentBack.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);
		}

		this.sendBroadcast(intentBack);

	}

}
