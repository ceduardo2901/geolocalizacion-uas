package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;


import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class CrearSitioServicio extends IntentService{

	protected static String TAG = "AgregarSitioServicio";
	private RestTemplate restTemp;
	
	public CrearSitioServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Bundle bundle = intent.getExtras();
		
		SitioDTO sitio = (SitioDTO) bundle.getSerializable("sitio");
		
		Map<String, SitioDTO> parms = new HashMap<String, SitioDTO>();
		parms.put("sitioDto", sitio);
		
		try{
			
			String respuesta = restTemp.postForObject(Constantes.CREAR_SITIOS_SERVICE_URL, sitio, String.class);
			
			 
			bundle.putString("respuesta", "respuesta");
			
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
		}
	
		
		Intent intentCrearSitio = new Intent(Constantes.CREAR_SITIO_FILTRO_ACTION);
		intentCrearSitio.putExtras(bundle);
		sendBroadcast(intentCrearSitio);
		
	}

}
