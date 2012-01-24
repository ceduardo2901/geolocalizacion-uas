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

/**
 * 
 * Este servicio envia una invitación por email para unirse a la red, Los datos
 * de entrada son el email al cual se le enviara la invitación y el nombre del
 * usuario
 * 
 * @author Martin
 * 
 */
public class EnviarInvitacionServicio extends IntentService {

	protected static String TAG = "EnviarInvitacionServicio";
	protected RestTemplate restTemp;
	protected HttpHeaders requestHeaders;

	public EnviarInvitacionServicio() {
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

		String direccion = intent.getStringExtra("direccion");

		ApplicationController app = ((ApplicationController) getApplicationContext());
		UsuarioDTO user = app.getUserLogin();

		Map<String, String> parms = new HashMap<String, String>();
		parms.put("direccion", direccion);
		parms.put("nombre", user.getNombre());

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		Intent intentBack = new Intent(
				Constantes.ENVIAR_INVITACIONES_FILTRO_ACTION);

		try {

			String respuesta = restTemp.getForObject(
					Constantes.ENVIAR_INVITACIONES_SERVICE_URL, String.class,
					parms);

			intentBack.putExtra("respuesta", respuesta);

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
