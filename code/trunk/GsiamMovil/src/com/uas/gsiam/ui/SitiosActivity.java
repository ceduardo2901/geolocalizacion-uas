package com.uas.gsiam.ui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.ArrayDeserializer;
import org.codehaus.jackson.map.deser.ArrayDeserializers;
import org.codehaus.jackson.map.type.ArrayType;
import org.codehaus.jackson.type.JavaType;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.servicios.EliminarSitioServicio;
import com.uas.gsiam.servicios.SitioServicio;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.SitioMovilDTO;
import com.uas.gsiam.utils.Util;

public class SitiosActivity extends ListActivity implements LocationListener,
		OnItemLongClickListener {

	protected static final String TAG = "SitioActivity";
	protected LocationManager locationManager;
	protected LocationListener locationListener;
	protected IntentFilter sitioAccion;
	protected IntentFilter intentEliminarSitio;
	protected SitiosAdapter adapter;
	protected List<SitioDTO> sitios;
	protected Location loc;
	protected ListView lw;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(android.R.id.list);
		lw = getListView();
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		sitioAccion = new IntentFilter(Constantes.SITIO_FILTRO_ACTION);
		intentEliminarSitio = new IntentFilter(
				Constantes.ELIMINAR_SITIO_FILTRO_ACTION);
		
		handleIntent(getIntent());
		
	}

	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}

	private void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			buscarSitio(query);
		}
	}
	
	private void buscarSitio(String query){
		Log.i(TAG, "Estoy en la busqueda");
		SitioDTO sitio = new SitioDTO();
		sitio.setNombre(query);
		Intent intentBuscarSitio = new Intent(this, SitioServicio.class);
		intentBuscarSitio.putExtra("sitio", sitio);
		
		startService(intentBuscarSitio);
		
		Util.showToast(this, Constantes.MSG_ESPERA_GENERICO);
		
	}

	public void onStart() {
		super.onStart();
		Log.d(TAG, "prueba");
	}

	public void onResume() {
		super.onResume();
		registerReceiver(sitiosReceiver, sitioAccion);
		registerReceiver(eliminarSitioReceiver, intentEliminarSitio);
		// Obtengo la ultima posicion conocida
		loc = getLastBestLocation(Constantes.MAX_DISTANCE,
				System.currentTimeMillis() - Constantes.MAX_TIME);
		if (!Intent.ACTION_SEARCH.equals(getIntent().getAction())) {
			
			actualizarSitios(loc);
		}
		
		startListening();

	}

	public List<SitioDTO> getSitios() {
		return sitios;
	}

	public void setSitios(List<SitioDTO> sitios) {
		this.sitios = sitios;
	}

	public void onPause() {
		super.onPause();
		unregisterReceiver(sitiosReceiver);
		unregisterReceiver(eliminarSitioReceiver);
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

			Intent agregarSitioIntent = new Intent(this,
					CrearSitioActivity.class);
			agregarSitioIntent.putExtra("ubicacion", loc);
			startActivity(agregarSitioIntent);
			break;
		case R.id.buscarSitioId:
			//String query = getIntent().getStringExtra(SearchManager.QUERY);
			startSearch(null, false, null, false);
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
			Bundle b = intent.getExtras();
			ArrayList<SitioDTO> sitios =  (ArrayList<SitioDTO>) b.getSerializable("sitios");
						
			if (sitios != null) {
				setSitios(sitios);
				mostrarSitios();
			}

		}
	};

	/**
	 * 
	 */
	protected BroadcastReceiver eliminarSitioReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.i(TAG, "mensaje de prueba estoy aca !!!!");
			Util.dismissProgressDialog();

			String respuesta = intent.getStringExtra("respuesta");

			actualizarSitios(loc);

		}
	};

	public List<SitioMovilDTO> parseSitios(String sitios) {
		ObjectMapper mapper = new ObjectMapper();
		SitioMovilDTO[] sitiosParse = null;
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
				sitios, loc);
		//ListView lstOpciones = (ListView) findViewById(R.id.LstOpciones);
		this.setListAdapter(adaptador);
		
		//lstOpciones.setAdapter(adaptador);
		
		lw.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SitioDTO sitio = sitios.get(position);
				Intent sitioDetalleIntent = new Intent(getApplicationContext(),
						SitioDetalleActivity.class);
				sitioDetalleIntent.putExtra("nombre", sitios.get(position)
						.getNombre());
				sitioDetalleIntent.putExtra("sitioId", sitios.get(position)
						.getIdSitio());
				sitioDetalleIntent.putExtra("direccion", sitios.get(position)
						.getDireccion());
				sitioDetalleIntent.putExtra("lat", sitio.getLat());
				sitioDetalleIntent.putExtra("lon", sitio.getLon());
				sitioDetalleIntent.putExtra("ubicacion", loc);
				startActivity(sitioDetalleIntent);

			}
		});
		lw.setOnItemLongClickListener(this);

	}

	private void actualizarSitio(SitioDTO sitio) {
		Intent intentModificar = new Intent(this, ModificarSitioActivity.class);
		intentModificar.putExtra("sitio", sitio);
		startActivity(intentModificar);

	}

	private void eliminarSitio(String sitioId) {
		Intent intent = new Intent(this, EliminarSitioServicio.class);
		intent.putExtra("sitio", sitioId);
		startService(intent);
		Util.showProgressDialog(this, Constantes.MSG_ESPERA_GENERICO);
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
			SitioDTO sitio = new SitioDTO();
			sitio.setLat(loc.getLatitude());
			sitio.setLon(loc.getLongitude());
			
			bundle.putDouble("lat", loc.getLatitude());
			bundle.putDouble("lon", loc.getLongitude());
			
			Intent intent = new Intent(this, SitioServicio.class);
			intent.putExtra("sitio", sitio);
			//intent.putExtras(bundle);
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
		

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		Util.showToast(getApplicationContext(), "EL gps no esta encendido");

	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		final SitioDTO sitio = sitios.get(position);
		CharSequence[] items = { "Modificar", "Eliminar" };
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Seleccionar una acción");
		builder.setItems(items, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int item) {

				if (item == 0) {

					actualizarSitio(sitio);
				} else {

					eliminarSitio(sitio.getIdSitio());
				}
			}
		});
		AlertDialog alert = builder.create();
		alert.show();

		return false;

	}
	

}
