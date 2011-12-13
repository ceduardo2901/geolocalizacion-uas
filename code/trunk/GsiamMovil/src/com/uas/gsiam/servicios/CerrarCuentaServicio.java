package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;


public class CerrarCuentaServicio extends IntentService{

	protected static String TAG = "CerrarCuentaServicio";
	protected RestTemplate restTemp;
	protected HttpHeaders requestHeaders;
	
	public CerrarCuentaServicio() {
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
		
		UsuarioDTO usuario = (UsuarioDTO) intent.getSerializableExtra("usuario");
		
		Map<String, UsuarioDTO> parms = new HashMap<String, UsuarioDTO>();
		parms.put("usuarioDto", usuario);
		
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		
		Intent intentBack = new Intent(Constantes.CERRAR_CUENTA_FILTRO_ACTION);
		
		try{
		
			restTemp.postForObject(Constantes.CERRAR_CUENTA_SERVICE_URL, usuario, String.class);	
			intentBack.putExtra("respuesta", Constantes.MSG_CUENTA_CERRADA_OK);
			
		}catch (RestResponseException e){
			String msg = e.getMensaje();
			intentBack.putExtra("error", msg);
		}catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());
			intentBack.putExtra("error", Constantes.MSG_ERROR_TIMEOUT);

		}

		sendBroadcast(intentBack);
		
	}

}
