package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class PublicarServicio extends IntentService{

	protected static String TAG = "PublicarServicio";
	protected RestTemplate restTemp;
	
	public PublicarServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
         
		PublicacionDTO publicacion = (PublicacionDTO) bundle.getSerializable("publicacion");
		
		Map<String, PublicacionDTO> parms = new HashMap<String, PublicacionDTO>();
		parms.put("publicacionDto", publicacion);
		Intent intentPublicacion = new Intent(Constantes.CREAR_PUBLICACION_FILTRO_ACTION);
		
		try{
			
			
			String respuesta = restTemp.postForObject(Constantes.CREAR_PUBLICACION_SERVICE_URL, publicacion, String.class);	
						
			bundle.putString("respuesta", respuesta);
			
			
			intentPublicacion.putExtra("bundle",bundle);
			
			sendBroadcast(intentPublicacion);
			
		}catch (RestClientException e) {
			Bundle mensajeError = new Bundle();
			//mensajeError.putString("error", e.getMessage());
			intentPublicacion.putExtra("error", e.getCause());
			sendBroadcast(intentPublicacion);
		}
		
		
		
	}

}
