package com.uas.gsiam.servicios;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

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
		prefs = getSharedPreferences(Constantes.SHARED_PREFERENCE_FILE,
				Context.MODE_PRIVATE);
		prefsEditor = prefs.edit();
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle bundle = intent.getExtras();

		SitioDTO sitio = (SitioDTO) intent.getSerializableExtra("sitio");

		SitioDTO[] respuesta = null;

		try {
			if (sitio.getLat() != null && sitio.getLon() != null) {
				Map<String, Double> parms = new HashMap<String, Double>();

				parms.put("lat", sitio.getLat());
				parms.put("lon", sitio.getLon());

				respuesta = restTemp.getForObject(
						Constantes.SITIOS_SERVICE_URL, SitioDTO[].class, parms);
			} else {
				Map<String, SitioDTO> parms = new HashMap<String, SitioDTO>();

				parms.put("sitio", sitio);
 
				respuesta = restTemp.getForObject(
						Constantes.BUSQUEDA_SITIOS_SERVICE_URL,
						SitioDTO[].class, parms);
			}

			Intent intentSitio = new Intent(Constantes.SITIO_FILTRO_ACTION);
			bundle.putSerializable("sitios", Util.getArrayListSitioDTO(respuesta));

			intentSitio.putExtras(bundle);
			sendBroadcast(intentSitio);
		} catch (RestClientException e) {
			Log.e(TAG, "Error al llamar servicio");
		}

	}

	

}
