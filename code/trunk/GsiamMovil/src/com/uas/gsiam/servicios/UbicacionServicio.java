package com.uas.gsiam.servicios;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.PosicionGPS;
import com.uas.gsiam.utils.Util;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;


public class UbicacionServicio extends IntentService implements LocationListener{

	public UbicacionServicio(String name) {
		super(name);
	}

	protected static String TAG = "SitioServicio";
	protected RestTemplate restTemp;
	protected Location loc;
	protected ApplicationController app;

	@Override
	protected void onHandleIntent(Intent intent) {
		
		restTemp = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
		
		loc = PosicionGPS.getPosicion(this);
		app = ((ApplicationController) getApplicationContext());
		
		PosicionUsuarioDTO posUsuario = new PosicionUsuarioDTO();
		posUsuario.setIdUsuario(app.getUserLogin().getId());
		posUsuario.setLat(loc.getLatitude());
		posUsuario.setLon(loc.getLongitude());
		
		Map<String, PosicionUsuarioDTO> parms = new HashMap<String, PosicionUsuarioDTO>();
		parms.put("posUsuario", posUsuario);
		
		try{
		
			String respuesta = restTemp.postForObject(Constantes.ACTUALIZAR_POSICION_USUARIO_SERVICE_URL, posUsuario, String.class);	
			
			//TODO: Que hacemos si hay error aca???
			if (!respuesta.equalsIgnoreCase(Constantes.RETURN_OK)){
				Log.i(TAG, "Error: " + respuesta);
			}
		}catch (RestClientException e){
			Log.i(TAG, "Error: " + e.getMessage());
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
