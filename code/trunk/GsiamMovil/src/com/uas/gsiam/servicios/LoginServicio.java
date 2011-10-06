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
import android.content.SharedPreferences;
import android.os.Bundle;

public class LoginServicio extends IntentService{

	protected static String TAG = "LoginServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;
	
	public LoginServicio() {
		super(TAG);
		
	}
	
	public void onCreate(){
		super.onCreate();
		
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
         
        String pass = bundle.getString("pass");
		String email = bundle.getString("email"); 
		Intent intentLogin = new Intent(Constantes.LOGIN_FILTRO_ACTION);
		try{
			
			Map<String, String> parms = new HashMap<String, String>();
			parms.put("email", email);
			parms.put("pass", pass);
			UsuarioDTO user = restTemp.getForObject(Constantes.LOGIN_SERVICE_URL, UsuarioDTO.class,parms);
			
			if(user.getEmail() != null){
				ApplicationController app = ((ApplicationController)getApplicationContext());
				app.setUserLogin(user);
			}
				bundle.putSerializable("usuario", user);
				intentLogin.putExtra("usuario",bundle);
			
			sendBroadcast(intentLogin);
			
		}catch (RestClientException e) {
			Bundle mensajeError = new Bundle();
			//mensajeError.putString("error", e.getMessage());
			intentLogin.putExtra("error", e.getCause());
			sendBroadcast(intentLogin);
		}
		
		
		
	}

}
