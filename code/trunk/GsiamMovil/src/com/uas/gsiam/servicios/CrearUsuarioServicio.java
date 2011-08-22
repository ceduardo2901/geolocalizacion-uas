package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


public class CrearUsuarioServicio extends IntentService{

	protected static String TAG = "CrearUsuarioServicio";
	protected SharedPreferences prefs;
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
         
		String nombre = bundle.getString("nombre");
		String pass = bundle.getString("pass");
		String email = bundle.getString("email"); 
		
		UsuarioDTO usuario = new UsuarioDTO();
		usuario.setNombre(nombre);
		usuario.setEmail(email);
		usuario.setPassword(pass);
		
		
		Map<String, UsuarioDTO> parms = new HashMap<String, UsuarioDTO>();
		parms.put("usuario", usuario);
		
		String respuesta = restTemp.getForObject(Constantes.CREAR_USUARIO_SERVICE_URL, String.class, parms);
		
		
		
		Intent intentLogin = new Intent(Constantes.LOGIN_FILTRO_ACTION);
		Bundle datos = new Bundle();
		
		sendBroadcast(intentLogin);
		
	}

}
