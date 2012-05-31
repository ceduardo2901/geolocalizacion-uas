package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.CategoriasUtil;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Servicio que permite la autenticacion de un usuario en el sistema. Se debera
 * enviar al servidor el email y contraseña para su autenticacion
 * 
 * @author Antonio
 * 
 */
public class LoginServicio extends IntentService {

	protected static String TAG = "LoginServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;

	public LoginServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				HttpUtils.getNewHttpClient());

		restTemp = new RestTemplate(requestFactory);
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		String pass = intent.getStringExtra("pass");
		String email = intent.getStringExtra("email");
		Intent intentLogin = new Intent(Constantes.LOGIN_FILTRO_ACTION);
		try {

			Map<String, String> parms = new HashMap<String, String>();
			parms.put("email", email);
			parms.put("pass", pass);
			restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
					String.class));

			UsuarioDTO user = restTemp.getForObject(
					Constantes.LOGIN_SERVICE_URL, UsuarioDTO.class, parms);

			if (user.getEmail() != null) {
				ApplicationController app = ((ApplicationController) getApplicationContext());
				app.setUserLogin(user);

				// /////////////////////////////////////////////////
				// Cargo las categorias

				CategoriaDTO[] respuesta = restTemp.getForObject(
						Constantes.GET_CATEGORIAS_SERVICE_URL,
						CategoriaDTO[].class);

				CategoriasUtil.cargarCategorias(this,
						Util.getArrayListCategoriaDTO(respuesta));
				// ///////////////////////////////////////////////

			}
			intentLogin.putExtra("usuario", user);
			sendBroadcast(intentLogin);

		} catch (RestResponseException e) {

			intentLogin.putExtra("error", (String) e.getResponseEntity()
					.getBody());
			Log.e(TAG, (String) e.getResponseEntity().getBody());
			sendBroadcast(intentLogin);
		} catch (ResourceAccessException e) {
			intentLogin.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);
			Log.e(TAG, e.getMessage());
			sendBroadcast(intentLogin);

		}

	}

}
