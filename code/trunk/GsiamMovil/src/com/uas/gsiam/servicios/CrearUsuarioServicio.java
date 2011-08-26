package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


public class CrearUsuarioServicio extends IntentService{

	protected static String TAG = "CrearUsuarioServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;
	
	public CrearUsuarioServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
		
		UsuarioDTO usuario = (UsuarioDTO) bundle.getSerializable("usuario");
		
		Map<String, UsuarioDTO> parms = new HashMap<String, UsuarioDTO>();
		parms.put("usuarioDto", usuario);
		
		try{
		
			String respuesta = restTemp.postForObject(Constantes.CREAR_USUARIO_SERVICE_URL, usuario, String.class);	
			bundle.putString("respuesta", respuesta);
			
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", e.getMessage());
		}
	
		
		Intent intentCrearUsuario = new Intent(Constantes.CREAR_USUARIO_FILTRO_ACTION);
		intentCrearUsuario.putExtras(bundle);
		sendBroadcast(intentCrearUsuario);
		
	}

}
