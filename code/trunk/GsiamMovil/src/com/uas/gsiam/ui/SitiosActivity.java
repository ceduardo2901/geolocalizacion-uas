package com.uas.gsiam.ui;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.uas.gsiam.servicios.SitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SitioMovilDTO;
import com.uas.gsiam.utils.Util;

public class SitiosActivity extends Activity implements LocationListener {

	protected static final String TAG = "SitioActivity";
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected IntentFilter sitioAccion;
	protected SitiosAdapter adapter;
	protected List<SitioMovilDTO> sitios;
	protected Location loc;
	

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
		
		loc = getLastBestLocation(
				Constantes.MAX_DISTANCE, System.currentTimeMillis()
						- Constantes.MAX_TIME);
		actualizarSitios(loc);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.sitios_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.agregarSitioId:
			
			Intent agregarSitioIntent = new Intent(this,CrearSitioActivity.class);
			agregarSitioIntent.putExtra("ubicacion", loc);
			startActivity(agregarSitioIntent);
			break;

		default:
			return super.onOptionsItemSelected(item);

		}
		return true;
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

	
		}
	};
	
	public List<SitioMovilDTO> parseSitios(String sitios){
		ObjectMapper mapper = new ObjectMapper();
		SitioMovilDTO[] sitiosParse=null;
		List<SitioMovilDTO> listaSitios;
		try {
			sitiosParse = mapper.readValue(sitios, SitioMovilDTO[].class);
			
		} catch (JsonParseException e) {
			Log.e(TAG, e.getMessage());
		} catch (JsonMappingException e) {
			Log.e(TAG, e.getMessage());
		} catch (IOException e) {
			Log.e(TAG, e.getMessage());
		}
		listaSitios = Arrays.asList(sitiosParse);
		
		return listaSitios;
	}

	public void mostrarSitios() {
		SitiosAdapter adaptador = new SitiosAdapter(this, R.layout.sitios,
				sitios);
		ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
		lstOpciones.setAdapter(adaptador);
		lstOpciones.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SitioMovilDTO sitio = sitios.get(position);
				Intent sitioDetalleIntent = new Intent(getApplicationContext(),SitioDetalleActivity.class);
				sitioDetalleIntent.putExtra("nombre", sitios.get(position).getNombre());
				sitioDetalleIntent.putExtra("direccion", sitios.get(position).getDireccion());
				sitioDetalleIntent.putExtra("lat", sitio.getLat());
				sitioDetalleIntent.putExtra("lon", sitio.getLon());
				
				startActivity(sitioDetalleIntent);
				
			}
		});
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
		this.loc = location;
		actualizarSitios(loc);

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