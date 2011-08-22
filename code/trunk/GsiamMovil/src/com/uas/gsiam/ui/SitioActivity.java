package com.uas.gsiam.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.servicios.SitiosServicio;
import com.uas.gsiam.ui.R;

public class SitioActivity extends Activity {

	private static final String TAG = "SitioActivity";
	private LocationManager locationManager;
	private LocationListener locationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sitios);

		actualizarUbicacion();
	}

	public void onStart() {
		super.onStart();
		Log.d(TAG, "prueba");
	}

	public void onResume() {
		super.onResume();

		actualizarUbicacion();

	}

	private void actualizarUbicacion() {
		// Obtenemos una referencia al LocationManager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		// Obtenemos la última posición conocida
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		// Mostramos la última posición conocida
		actualizarSitios(location);

		// Nos registramos para recibir actualizaciones de la posición
		locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				actualizarSitios(location);
			}

			public void onProviderDisabled(String provider) {

			}

			public void onProviderEnabled(String provider) {

			}

			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}
		};

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				15000, 0, locationListener);
	}

	private void actualizarSitios(Location loc) {
		if (loc != null) {
			Bundle bundle = new Bundle();
			bundle.putParcelable("ubicacionActual", loc);

			Intent intent = new Intent();
			intent.setClass(this, SitiosServicio.class);
			intent.putExtras(bundle);
			startActivity(intent);
		}
	}

}
