package com.uas.gsiam.servicios;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;


public class CrearUsuarioServicio extends IntentService{

	protected static String TAG = "CrearUsuarioServicio";
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
		
		
		try{
		
			String respuesta = restTemp.postForObject(Constantes.CREAR_USUARIO_SERVICE_URL, usuario, String.class);	
			bundle.putString("respuesta", respuesta);
			
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
		}
	
		
		Intent intentCrearUsuario = new Intent(Constantes.CREAR_USUARIO_FILTRO_ACTION);
		intentCrearUsuario.putExtras(bundle);
		sendBroadcast(intentCrearUsuario);
		
	}

}
