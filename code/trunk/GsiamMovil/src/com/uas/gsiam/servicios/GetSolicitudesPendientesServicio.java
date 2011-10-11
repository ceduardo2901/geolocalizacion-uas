package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.Util;

public class GetSolicitudesPendientesServicio extends IntentService {

	protected static String TAG = "GetSolicitudesPendientesServicio";
	
	protected RestTemplate restTemp;

	public GetSolicitudesPendientesServicio() {
		super(TAG);

	}

	public void onCreate() {
		super.onCreate();
		restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());

	}

	@Override
	protected void onHandleIntent(Intent intent) {

		Bundle bundle = new Bundle();

		ApplicationController app = ((ApplicationController) getApplicationContext());
		UsuarioDTO user = app.getUserLogin();

		Map<String, Integer> parms = new HashMap<String, Integer>();
		parms.put("id", user.getId());

		try {

			UsuarioDTO[] respuesta = restTemp.getForObject(
					Constantes.GET_SOLICITUDES_RECIBIDAS_SERVICE_URL, UsuarioDTO[].class,
					parms);

			bundle.putSerializable("listaRecibidas", Util.getArrayListUsuarioDTO(respuesta));
			
			        respuesta = restTemp.getForObject(
					Constantes.GET_SOLICITUDES_ENVIADAS_SERVICE_URL, UsuarioDTO[].class,
					parms);
			
			bundle.putSerializable("listaEnviadas", Util.getArrayListUsuarioDTO(respuesta));

		} catch (RestClientException e) {
			Log.i(TAG, "Error: " + e.getMessage());
			bundle.putString("respuesta", Constantes.MSG_ERROR_SERVIDOR);
		}

		Intent intentGetSolRecibidas = new Intent(Constantes.GET_SOLICITUDES_FILTRO_ACTION);
		intentGetSolRecibidas.putExtras(bundle);
		this.sendBroadcast(intentGetSolRecibidas);

	}

}
