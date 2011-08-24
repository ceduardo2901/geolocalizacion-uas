package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import com.facebook.android.Util;
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
			
			if (respuesta.equals(Constantes.RETURN_OK)){
				Util.showAlert(this, "Aviso", "El usuario se ha creado exitosamente");
			}
			else{
				Util.showAlert(this, "Aviso", respuesta);
			}
			
			
		}catch (RestClientException e){
			Log.i(TAG, "error");
			Util.showAlert(this, "Error", "Lo sentimos, el servidor no se encuentra disponible");
		}
	
		
		Intent intentLogin = new Intent(Constantes.CREAR_USUARIO_FILTRO_ACTION);
		sendBroadcast(intentLogin);
		
	}

}
