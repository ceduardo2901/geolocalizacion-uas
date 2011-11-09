package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.PosicionGPS;

public class ActualizarPosicionServicio extends Service {

	private static Timer timer = new Timer();
	private Context ctx;
	private static final String TAG = "ActualizarPosicionServicio";
	private RestTemplate restTemp;
	protected Location loc;
	protected ApplicationController app;

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	public void onCreate() {

		super.onCreate();
		Log.i(TAG, "onCreate");
		ctx = this;

		restTemp = new RestTemplate(
				new HttpComponentsClientHttpRequestFactory());

		app = ((ApplicationController) getApplicationContext());
//		if (loc != null) {
//			startService();
//		}
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		loc = intent.getParcelableExtra("loc");
		startService();
		return startId;
	}

	private void startService() {
		Log.i(TAG, "startService");
		timer.scheduleAtFixedRate(new mainTask(), 0, 30000);
	}

	private class mainTask extends TimerTask {
		public void run() {

			PosicionUsuarioDTO posUsuario = new PosicionUsuarioDTO();
			posUsuario.setIdUsuario(app.getUserLogin().getId());
			posUsuario.setLat(loc.getLatitude());
			posUsuario.setLon(loc.getLongitude());

			Map<String, PosicionUsuarioDTO> parms = new HashMap<String, PosicionUsuarioDTO>();
			parms.put("posUsuario", posUsuario);

			try {

				String respuesta = restTemp.postForObject(
						Constantes.ACTUALIZAR_POSICION_USUARIO_SERVICE_URL,
						posUsuario, String.class);

				// TODO: Que hacemos si hay error aca???
				if (!respuesta.equalsIgnoreCase(Constantes.RETURN_OK)) {
					Log.i(TAG, "Error: " + respuesta);
				}
			} catch (RestClientException e) {
				Log.i(TAG, "Error: " + e.getMessage());
			}
		}
	}

	public void onDestroy() {
		Log.i(TAG, "onDestroy");
		super.onDestroy();
		if (timer != null) {
			timer.cancel();
		}

	}

}
