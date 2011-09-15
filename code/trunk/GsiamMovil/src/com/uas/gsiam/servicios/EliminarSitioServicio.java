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

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;

public class EliminarSitioServicio extends IntentService{

	protected static String TAG = "EliminarSitioServicio";
	
	protected RestTemplate restTemp;
	
	public EliminarSitioServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
	    String sitio = intent.getStringExtra("sitio");
		 
		Intent intentEliminarSitio = new Intent(Constantes.ELIMINAR_SITIO_FILTRO_ACTION);
		try{
			
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("sitio", sitio);
			
			restTemp.delete(Constantes.ELIMINAR_SITIOS_SERVICE_URL, sitio);
		
			//intentEliminarSitio.putExtra("respuesta", respuesta);	
			sendBroadcast(intentEliminarSitio);
			
		}catch (RestClientException e) {
			Bundle mensajeError = new Bundle();
			//mensajeError.putString("error", e.getMessage());
			intentEliminarSitio.putExtra("error", e.getCause());
			sendBroadcast(intentEliminarSitio);
		}
		
		
		
	}
}
