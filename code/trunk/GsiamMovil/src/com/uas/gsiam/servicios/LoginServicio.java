package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.CategoriasUtil;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;

public class LoginServicio extends IntentService {

	protected static String TAG = "LoginServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;

	public LoginServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();

		restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle bundle = intent.getExtras();

		String pass = bundle.getString("pass");
		String email = bundle.getString("email");
		Intent intentLogin = new Intent(Constantes.LOGIN_FILTRO_ACTION);
		try {

			Map<String, String> parms = new HashMap<String, String>();
			parms.put("email", email);
			parms.put("pass", pass);
			restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
					String.class));
			UsuarioDTO user = restTemp.getForObject(
					Constantes.LOGIN_SERVICE_URL, UsuarioDTO.class, parms);
			// ResponseEntity<String> responseEntity = restTemp.exchange(
			// Constantes.LOGIN_SERVICE_URL, HttpMethod.GET, null,
			// String.class, parms);
			
			if (user.getEmail() != null) {
				ApplicationController app = ((ApplicationController) getApplicationContext());
				app.setUserLogin(user);

				// /////////////////////////////////////////////////
				// Cargo las categorias
				// TODO Ver como hacer al iniciar la aplicacion y no en el login

				CategoriaDTO[] respuesta = restTemp.getForObject(
						Constantes.GET_CATEGORIAS_SERVICE_URL,
						CategoriaDTO[].class);

				CategoriasUtil.cargarCategorias(this,
						Util.getArrayListCategoriaDTO(respuesta));
				// ///////////////////////////////////////////////

			}
			bundle.putSerializable("usuario", user);
			intentLogin.putExtra("usuario", bundle);
			sendBroadcast(intentLogin);

		} catch (RestResponseException e) {

			intentLogin.putExtra("error", (String) e.getResponseEntity()
					.getBody());
			Log.e(TAG, (String) e.getResponseEntity().getBody());
			sendBroadcast(intentLogin);
		}

	}

}
