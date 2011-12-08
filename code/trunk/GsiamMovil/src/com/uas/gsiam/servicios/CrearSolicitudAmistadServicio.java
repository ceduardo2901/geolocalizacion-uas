package com.uas.gsiam.servicios;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;


public class CrearSolicitudAmistadServicio extends IntentService{

	protected static String TAG = "CrearSolicitudAmistadServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;
	protected HttpHeaders requestHeaders;
	protected HttpEntity<SolicitudContactoDTO> requestEntity;
	
	public CrearSolicitudAmistadServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		requestHeaders = new HttpHeaders();		
		requestHeaders.setContentType(new MediaType("application", "json"));
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory(
				HttpUtils.getNewHttpClient()));

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		SolicitudContactoDTO solicitud = (SolicitudContactoDTO) intent.getSerializableExtra("solicitud");
		requestEntity = new HttpEntity<SolicitudContactoDTO>(solicitud,requestHeaders);
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(String.class));
		Intent intentBack = new Intent(Constantes.CREAR_SOLICITUD_AMISTAD_FILTRO_ACTION);
		
		try{
						
			ResponseEntity<String> respuesta = restTemp.exchange(Constantes.CREAR_SOLICITUD_AMISTAD_SERVICE_URL, 
					HttpMethod.POST, requestEntity, String.class);	
			
			if (respuesta.getStatusCode() == HttpStatus.OK) {
				intentBack.putExtra("respuesta", Constantes.MSG_SOLICITUD_CREADA_OK);
			} else {
				intentBack.putExtra("error", Constantes.MSG_ERROR_INESPERADO);

			}
			
			
		}catch (RestResponseException e){
			String msg = (String) e.getResponseEntity().getBody();
			Log.d(TAG, "Error: " + msg);
			intentBack.putExtra("error", msg);
		}
	
		sendBroadcast(intentBack);

	}
	

	
}
