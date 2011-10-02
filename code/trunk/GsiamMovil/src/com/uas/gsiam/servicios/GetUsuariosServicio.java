package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;


public class GetUsuariosServicio extends IntentService{

	protected static String TAG = "GetUsuariosServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;
	
	public GetUsuariosServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
		
		String nombre = bundle.getString("nombre");
		
		Map<String, String> parms = new HashMap<String, String>();
		parms.put("nombre", nombre);
		
		try{
		
			UsuarioDTO[] respuesta = restTemp.getForObject(Constantes.GET_USUARIOS_SERVICE_URL, UsuarioDTO[].class, parms);
			
			bundle.putSerializable("lista", Util.getArrayListUsuarioDTO(respuesta));
			
			
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
		}
	
		Intent intentGetUsuarios = new Intent(Constantes.GET_USUARIOS_FILTRO_ACTION);
		intentGetUsuarios.putExtras(bundle);
		this.sendBroadcast(intentGetUsuarios);
		
	}
	
	

	
	
	
	

}
