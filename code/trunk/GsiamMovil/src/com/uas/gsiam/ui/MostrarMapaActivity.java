package com.uas.gsiam.ui;

import java.util.List;

import android.content.Intent;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.uas.gsiam.utils.PositionOverlay;

public class MostrarMapaActivity extends MapActivity{

	
	private MapView mapa;
	private GeoPoint geoPoint;
	private GeoPoint geoPointUbicacion;
	private PositionOverlay sitioPosOverlay;
	private PositionOverlay myposOverlay;
	private Location loc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mostrar_mapa);
		mapa = (MapView) findViewById(R.id.mapaId);
		
	}
	
	protected void onResume(){
		super.onResume();
		Double lat = getIntent().getDoubleExtra("lat", 0);
		Double lon = getIntent().getDoubleExtra("lon", 0);
		loc = getIntent().getParcelableExtra("ubicacion");
		mostrarMapa(lat,lon);
		
		
	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		
		return false;
	}
	
	private void mostrarMapa(Double lat, Double lon) {

		if (mapa == null)
			mapa = (MapView) findViewById(R.id.mapaId);
		MapController mapControl = mapa.getController();
		geoPoint = new GeoPoint(
				(int) (lat*1000000),
				(int) (lon*1000000));
		geoPointUbicacion = new GeoPoint(
				(int) (loc.getLatitude()*1000000),
				(int) (loc.getLongitude()*1000000));
		mapa.setBuiltInZoomControls(true);		
		
		mapControl.setZoom(15);
		//mapControl.animateTo(geoPoint);
			

		if (sitioPosOverlay == null && myposOverlay == null){
			sitioPosOverlay = new PositionOverlay();
			myposOverlay = new PositionOverlay();
		}
		else
			mapa.removeAllViews();
		final List<Overlay> overlays = mapa.getOverlays();
		overlays.clear();
		
		overlays.add(sitioPosOverlay);
		overlays.add(myposOverlay);
		sitioPosOverlay.setLocation(lat,lon);
		myposOverlay.setLocation(loc);
		myposOverlay.setNombre("Usted esta Aqui");
		
		mapa.setClickable(true);
		mapControl.setCenter(geoPointUbicacion);
		
	}

}
