package com.uas.gsiam.ui;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.uas.gsiam.servicios.SitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SitioMovilDTO;
import com.uas.gsiam.utils.Util;

public class SitioActivity extends Activity implements LocationListener {

	protected static final String TAG = "SitioActivity";
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected IntentFilter sitioAccion;
	protected SitiosAdapter adapter;
	protected List<SitioMovilDTO> sitios;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_sitios);
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		sitioAccion = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);

	}

	public void onStart() {
		super.onStart();
		Log.d(TAG, "prueba");
	}

	public void onResume() {
		super.onResume();
		registerReceiver(sitiosReceiver, sitioAccion);
		// Obtengo la ultima posicion conocida
		Location lastKnownLocation = getLastBestLocation(
				Constantes.MAX_DISTANCE, System.currentTimeMillis()
						- Constantes.MAX_TIME);
		actualizarSitios(lastKnownLocation);
		startListening();

	}

	public List<SitioMovilDTO> getSitios() {
		return sitios;
	}

	public void setSitios(List<SitioMovilDTO> sitios) {
		this.sitios = sitios;
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(sitiosReceiver);
		stopListening();
		
	}

	/**
	 * 
	 */
	protected BroadcastReceiver sitiosReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();
			
			String sitios = intent.getStringExtra("sitios");
			
			if (sitios != null) {
				setSitios(parseSitios(sitios));
				mostrarSitios();
			}

			// Intent actividadPrincipal = new Intent(getApplicationContext(),
			// MainActivity.class);

			// startActivity(actividadPrincipal);

		}
	};
	
	public List<SitioMovilDTO> parseSitios(String sitios){
		ObjectMapper mapper = new ObjectMapper();
		SitioMovilDTO[] sitiosParse=null;
		List<SitioMovilDTO> listaSitios;
		try {
			sitiosParse = mapper.readValue(sitios, SitioMovilDTO[].class);
			
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listaSitios = Arrays.asList(sitiosParse);
		
		return listaSitios;
	}

	public void mostrarSitios() {
		SitiosAdapter adaptador = new SitiosAdapter(this, R.layout.sitios,
				sitios);
		ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
		lstOpciones.setAdapter(adaptador);
	}

	private void startListening() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, this);
	}

	private void stopListening() {
		if (locationManager != null)
			locationManager.removeUpdates(this);
	}

	private void actualizarSitios(Location loc) {
		if (loc != null) {
			Bundle bundle = new Bundle();
			bundle.putDouble("lat", loc.getLatitude());
			bundle.putDouble("lon", loc.getLongitude());
			Intent intent = new Intent(this, SitioServicio.class);
			intent.putExtras(bundle);
			startService(intent);

		}
	}

	protected Location getLastBestLocation(int minDistance, long minTime) {
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

		List<String> matchingProviders = locationManager.getAllProviders();
		for (String provider : matchingProviders) {
			Location location = locationManager.getLastKnownLocation(provider);
			if (location != null) {
				float accuracy = location.getAccuracy();
				long time = location.getTime();

				if ((time > minTime && accuracy < bestAccuracy)) {
					bestResult = location;
					bestAccuracy = accuracy;
					bestTime = time;
				} else if (time < minTime && bestAccuracy == Float.MAX_VALUE
						&& time > bestTime) {
					bestResult = location;
					bestTime = time;
				}
			}
		}
		return bestResult;
	}

	@Override
	public void onLocationChanged(Location location) {
		actualizarSitios(location);

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

}
