package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.os.Bundle;

import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SitioMovilDTO;

public class SitioServicio extends IntentService {

	protected static String TAG = "SitioServicio";
	protected SharedPreferences prefs;
	protected Editor prefsEditor;
	protected RestTemplate restTemp;

	public SitioServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();
		restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());
		prefs = getSharedPreferences(Constantes.SHARED_PREFERENCE_FILE, Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Bundle bundle = intent.getExtras();
         
		Double lat = bundle.getDouble("lat");
		Double lon = bundle.getDouble("lon");
						
		Map<String, Double> parms = new HashMap<String, Double>();
		
		parms.put("lat", lat);
		parms.put("lon", lon);
		
		SitioMovilDTO[] respuesta = restTemp.getForObject(Constantes.SITIOS_SERVICE_URL, SitioMovilDTO[].class, parms);
				
		Intent intentSitio = new Intent(Constantes.SITIO_FILTRO_ACTION);
				
		sendBroadcast(intentSitio);
		
		
	}

}
