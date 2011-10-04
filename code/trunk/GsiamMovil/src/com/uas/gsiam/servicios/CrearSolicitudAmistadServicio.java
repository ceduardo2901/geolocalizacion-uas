package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;


public class CrearSolicitudAmistadServicio extends IntentService{

	protected static String TAG = "CrearSolicitudAmistadServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;
	
	public CrearSolicitudAmistadServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
		
		int idSolicitante = (int) bundle.getInt("idSolicitante");
		int idAprobador = (int) bundle.getInt("idAprobador");

		Map<String, Integer> parms = new HashMap<String, Integer>();
		parms.put("idSolicitante", idSolicitante);
		parms.put("idAprobador", idAprobador);
		
		try{
			String respuesta = restTemp.getForObject(Constantes.CREAR_SOLICITUD_AMISTAD_SERVICE_URL, String.class, parms);
				
			bundle.putString("respuesta", respuesta);
			
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
		}
	
		
		Intent intentCrearSolicitudAmistad = new Intent(Constantes.CREAR_SOLICITUD_AMISTAD_FILTRO_ACTION);
		intentCrearSolicitudAmistad.putExtras(bundle);
		sendBroadcast(intentCrearSolicitudAmistad);
		
	}

}
