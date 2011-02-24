package com.proyecto;

import java.util.List;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class LocationActivity extends MapActivity implements Runnable,
		OnClickListener {

	private ProgressDialog pd;
	private MapView mapView;
	private Button btUpdate;
	private TextView txtLocation;
	private Location currentLocation;
	private LocationManager mLocationManager;
	private Location mLocation;
	private GeoPoint geoPoint;
	private PositionOverlay myposOverlay;	
	
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			pd.dismiss();
			mLocationManager.removeUpdates(mLocationListener);
			currentLocation = mLocationListener.getLocation();
			if (currentLocation != null) {
				txtLocation.setText(currentLocation.getLongitude() + " "
						+ currentLocation.getLatitude());
				showMap();
			}
		}
	};
	private MyLocationListener mLocationListener;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btUpdate = (Button) this.findViewById(R.id.btUpdate);
		btUpdate.setOnClickListener(this);
		mapView = (MapView) this.findViewById(R.id.myMapView);
		
		txtLocation = (TextView) this.findViewById(R.id.tvGPS);

	}

	@Override
	public void run() {
		mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			Looper.prepare();

			mLocationListener = new MyLocationListener();
			mLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

			mLocationListener.setHandler(handler);
			Looper.loop();

			Looper.myLooper().quit();
		} else {
			Toast.makeText(getBaseContext(), "Signal GPS not found",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onClick(View arg0) {
		writeSignalGPS();
	}

	private void writeSignalGPS() {
		DialogInterface.OnCancelListener dialogCancel = new DialogInterface.OnCancelListener() {
			public void onCancel(DialogInterface dialog) {
			}
		};
		pd = ProgressDialog.show(this, "Searching...", "Searching GPS Signal",
				true, true, dialogCancel);
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	protected boolean isRouteDisplayed() {

		return false;
	}

	private void showMap() {

		if (mapView == null)
			mapView = (MapView) findViewById(R.id.myMapView);
		MapController mapControl = mapView.getController();
		geoPoint = new GeoPoint(
				(int) (currentLocation.getLatitude()*1000000),
				(int) (currentLocation.getLongitude()*1000000));
		//mapControl.setZoom(20);
		mapControl.animateTo(geoPoint);
		if (myposOverlay == null)
			myposOverlay = new PositionOverlay();
		else
			mapView.removeAllViews();
		final List<Overlay> overlays = mapView.getOverlays();
		overlays.clear();
		overlays.add(myposOverlay);
		myposOverlay.setLocation(currentLocation);
		mapView.setClickable(true);
	}

	
}