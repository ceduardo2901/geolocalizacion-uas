package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class EnviarInvitacionServicio extends IntentService{

	protected static String TAG = "EnviarInvitacionServicio";
	protected RestTemplate restTemp;
	
	public EnviarInvitacionServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
        String direcciones = bundle.getString("direcciones");
        
        ApplicationController app = ((ApplicationController) getApplicationContext());
		UsuarioDTO user = app.getUserLogin();
		
		try{
			
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("direcciones", direcciones);
			parms.put("nombre", user.getNombre());
			String respuesta = restTemp.getForObject(Constantes.ENVIAR_INVITACIONES_SERVICE_URL, String.class, parms);
			
			bundle.putString("respuesta", respuesta);
			
		}catch (RestClientException e) {
			
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
			
		}
		
		Intent intentInvitacion = new Intent(Constantes.ENVIAR_INVITACIONES_FILTRO_ACTION);
		intentInvitacion.putExtras(bundle);
		sendBroadcast(intentInvitacion);
		
	}

}
