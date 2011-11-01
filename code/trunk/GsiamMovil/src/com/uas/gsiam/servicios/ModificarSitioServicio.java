package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.utils.Constantes;

public class ModificarSitioServicio extends IntentService {

	protected static String TAG = "ModificarSitioServicio";
	protected SharedPreferences prefs;
	protected RestTemplate restTemp;

	public ModificarSitioServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();
		restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle bundle = intent.getExtras();

		SitioDTO sitio = (SitioDTO) bundle.getParcelable("sitio");

		Map<String, SitioDTO> parms = new HashMap<String, SitioDTO>();
		parms.put("sitioDto", sitio);

		try {

			restTemp.put(Constantes.MODIFICAR_SITIOS_SERVICE_URL, sitio);
			//bundle.putString("respuesta", respuesta);

		} catch (RestClientException e) {
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("error", Constantes.MSG_ERROR_SERVIDOR);
		}

		Intent intentModificarSitio = new Intent(
				Constantes.MODIFICAR_SITIO_FILTRO_ACTION);
		intentModificarSitio.putExtras(bundle);
		sendBroadcast(intentModificarSitio);

	}
}
