package com.uas.gsiam.servicios;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.utils.Constantes;


public class ResponderSolicitudServicio extends IntentService{

	protected static String TAG = "ResponderSolicitudServicio";
	protected RestTemplate restTemp;
	
	public ResponderSolicitudServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
		int accion = bundle.getInt("accion");
		SolicitudContactoDTO solicitud = (SolicitudContactoDTO) bundle.getSerializable("solicitud");
		String respuesta;
		try{
				
			if(accion == Constantes.ACEPTAR_SOLICITUD){
				
				respuesta = restTemp.postForObject(Constantes.ACEPTAR_SOLICITUD_AMISTAD_SERVICE_URL, solicitud, String.class);	
			}else{
				
				respuesta = restTemp.postForObject(Constantes.RECHAZAR_SOLICITUD_AMISTAD_SERVICE_URL, solicitud, String.class);	
			}

				bundle.putString("respuesta", respuesta);
			
			
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
		}
	
		
		Intent intentResponderSolicitud = new Intent(Constantes.RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION);
		intentResponderSolicitud.putExtras(bundle);
		sendBroadcast(intentResponderSolicitud);
		
		
		
		
	}

}
