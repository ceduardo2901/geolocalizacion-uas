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
import android.content.SharedPreferences;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Servicio que obtiene todos los usuarios con un nombre dado que no son amigos
 * del usuario autenticado, se debe ingresar el identificador del usuario y el
 * nombre por el cual se filtrara
 * 
 * @author Martín
 * 
 */
public class GetUsuariosServicio extends IntentService {

	protected static String TAG = "GetUsuariosServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;
	protected HttpHeaders requestHeaders;

	public GetUsuariosServicio() {
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

		String nombre = intent.getStringExtra("nombre");
		ApplicationController app = ((ApplicationController) getApplicationContext());
		UsuarioDTO user = app.getUserLogin();

		Map<String, String> parms = new HashMap<String, String>();
		parms.put("id", String.valueOf(user.getId()));
		parms.put("nombre", nombre);

		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		Intent intentBack = new Intent(Constantes.GET_USUARIOS_FILTRO_ACTION);

		try {

			UsuarioDTO[] respuesta = restTemp.getForObject(
					Constantes.GET_USUARIOS_SERVICE_URL, UsuarioDTO[].class,
					parms);
			intentBack.putExtra("respuesta",
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
