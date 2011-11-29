package com.uas.gsiam.servicios;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;


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
			restTemp.setErrorHandler(new RestResponseErrorHandler<String>(String.class));
			String respuesta = restTemp.postForObject(Constantes.CREAR_USUARIO_SERVICE_URL, usuario, String.class);	
			bundle.putString("respuesta", respuesta);
			
		}catch (RestResponseException e){
			Log.i(TAG, "Error: " + (String) e.getResponseEntity().getBody());
			bundle.putString("respuesta", (String) e.getResponseEntity().getBody());
			
		}
	
		Intent intentCrearUsuario = new Intent(Constantes.CREAR_USUARIO_FILTRO_ACTION);
		intentCrearUsuario.putExtras(bundle);
		sendBroadcast(intentCrearUsuario);
		
	}

}
