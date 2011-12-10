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
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.HttpUtils;
import com.uas.gsiam.utils.PosicionGPS;
import com.uas.gsiam.utils.RestResponseErrorHandler;
import com.uas.gsiam.utils.RestResponseException;
import com.uas.gsiam.utils.Util;


public class UbicacionServicio extends IntentService implements LocationListener{

	protected static String TAG = "UbicacionServicio";
	protected RestTemplate restTemp;
	protected HttpHeaders requestHeaders;
	protected Location loc;
	protected ApplicationController app;

	public UbicacionServicio(String name) {
		super(name);
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
		
		loc = PosicionGPS.getPosicion(this);
		app = ((ApplicationController) getApplicationContext());
		
		PosicionUsuarioDTO posUsuario = new PosicionUsuarioDTO();
		posUsuario.setIdUsuario(app.getUserLogin().getId());
		posUsuario.setLat(loc.getLatitude());
		posUsuario.setLon(loc.getLongitude());
		
		Map<String, PosicionUsuarioDTO> parms = new HashMap<String, PosicionUsuarioDTO>();
		parms.put("posUsuario", posUsuario);
		
		restTemp.setErrorHandler(new RestResponseErrorHandler<String>(
				String.class));
		
		try{
		
			restTemp.postForObject(Constantes.ACTUALIZAR_POSICION_USUARIO_SERVICE_URL, posUsuario, String.class);	
			
			//TODO: Que hacemos si hay error aca???
			
		}catch (RestResponseException e){
			String msg = e.getMensaje();
			Log.i(TAG, "Error: " + msg);
		}catch (ResourceAccessException e) {
			Log.e(TAG, e.getMessage());

		}
		
	}	

	
	public void finalizarServicio(){
	}

	@Override
	public void onLocationChanged(Location location) {
		this.loc = location;
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		Util.showToast(getApplicationContext(), "EL gps no esta encendido");
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	



}
