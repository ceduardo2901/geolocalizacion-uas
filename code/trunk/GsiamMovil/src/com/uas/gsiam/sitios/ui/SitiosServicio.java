package com.uas.gsiam.sitios.ui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.uas.gsiam.principal.ui.MainActivity;
import com.uas.gsiam.sitios.utils.ListaSitios;

public class SitiosServicio extends Activity {

	private static String url = "http://10.0.2.2:8080/GsiamWeb2/sitios/{lat}/{lon}";
	private RestTemplate restTemp;
	private static final String TAG = "SitiosServicio";
	private Location loc;
	private LocationManager locationManager;
	private LocationListener locationListener;

	private ProgressDialog progressDialog;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		
		locationManager = (LocationManager) this
				.getSystemService(Context.LOCATION_SERVICE);
		// this.loc = bundle.getParcelable("ubicacionActual");

	}

	@Override
	public void onStart() {
		super.onStart();

	}

	public void onStop() {
		super.onStop();
		stopListening();

	}

	public void onResume() {
		super.onResume();
		// ILastLocationFinder locationFinder =
		// PlatformSpecificImplementationFactory.getLastLocationFinder(this);
		// loc =
		// locationFinder.getLastBestLocation(ConstantesSitios.MAX_DISTANCE,
		// System.currentTimeMillis()-ConstantesSitios.MAX_TIME);
		loc = locationManager
				.getLastKnownLocation(locationManager.GPS_PROVIDER);
		new DownloadStatesTask(loc.getLatitude(), loc.getLongitude());
		startListening();
		

	}

	private void startListening() {
//		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
//				0, this);
	}

	private void stopListening() {
//		if (locationManager != null)
//			locationManager.removeUpdates(this);
	}

	private void actualizarSitios() {
		if(loc != null){
			new DownloadStatesTask(loc.getLatitude(), loc.getLongitude());
		}
	}

	protected Location getLastBestLocation(int minDistance, long minTime) {
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

		// Iterate through all the providers on the system, keeping
		// note of the most accurate result within the acceptable time
		// limit.
		// If no result is found within maxTime, return the newest Location.
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

	private void showLoadingProgressDialog() {
		progressDialog = ProgressDialog.show(this, "",
				"Loading. Please wait...", true);
	}

	private void dismissProgressDialog() {
		if (progressDialog != null) {

			progressDialog.dismiss();
		}
	}

	private void logException(Exception e) {
		Log.e(TAG, e.getMessage(), e);
		Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));
	}

	// ***************************************
	// Private classes
	// ***************************************
	private class DownloadStatesTask extends AsyncTask<Void, Void, ListaSitios> {
		private Double latitud;
		private Double longitud;

		public DownloadStatesTask(){
			
		}
		
		public DownloadStatesTask(Double lat, Double lon) {
			latitud = lat;
			longitud = lon;

		}

		@Override
		protected void onPreExecute() {
			// before the network request begins, show a progress indicator
			showLoadingProgressDialog();
		}

		@Override
		protected ListaSitios doInBackground(Void... params) {
			ListaSitios sitios = null;
			restTemp = new RestTemplate(
					new HttpComponentsClientHttpRequestFactory());
				Map<String, String> parms = new HashMap<String, String>();
				parms.put("lat", latitud.toString());
				parms.put("lon", longitud.toString());
				// parms.put("lat", "-34.8948244");
				// parms.put("lon", "-56.1195473");
				sitios = restTemp.getForObject(url, ListaSitios.class, parms);

				return sitios;
			
		}

		@Override
		protected void onPostExecute(ListaSitios sitios) {
			// hide the progress indicator when the network request is complete
			dismissProgressDialog();
			Intent intentMainAct = new Intent(getApplicationContext(),
					MainActivity.class);
			startActivity(intentMainAct);
			// return the list of states
			// refreshStates(usuario);
		}
	}

	
}
