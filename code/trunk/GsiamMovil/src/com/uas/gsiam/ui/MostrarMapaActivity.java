package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.uas.gsiam.utils.PositionOverlay;

public class MostrarMapaActivity extends GDMapActivity{

	
	private MapView mapa;
	private GeoPoint geoPoint;
	private GeoPoint geoPointUbicacion;
	private PositionOverlay sitioPosOverlay;
	private PositionOverlay myposOverlay;
	private Location loc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.mostrar_mapa);
		mapa = (MapView) findViewById(R.id.mapaId);
		Double lat = getIntent().getDoubleExtra("lat", 0);
		Double lon = getIntent().getDoubleExtra("lon", 0);
		loc = getIntent().getParcelableExtra("ubicacion");
		mostrarMapa(lat,lon);
		
		inicializarBar();
		
	}
	
	private void inicializarBar() {
		
		setTitle(R.string.app_name);
	}
	
	protected void onResume(){
		super.onResume();
		
		
		
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
		mapControl.animateTo(geoPointUbicacion);
		
		
		BasicItemizedOverlay miPosicionOverlay = new BasicItemizedOverlay(getResources().getDrawable(R.drawable.gd_map_pin_pin));
		BasicItemizedOverlay sitioOverlay = new BasicItemizedOverlay(getResources().getDrawable(R.drawable.gd_map_pin_base));
		miPosicionOverlay.addOverlay(new OverlayItem(geoPointUbicacion, null, null));
		sitioOverlay.addOverlay(new OverlayItem(geoPoint, null, null));
//		if (sitioPosOverlay == null && myposOverlay == null){
//			sitioPosOverlay = new PositionOverlay();
//			myposOverlay = new PositionOverlay();
//		}
//		else
//			mapa.removeAllViews();
//		final List<Overlay> overlays = mapa.getOverlays();
//		overlays.clear();
//		
//		overlays.add(sitioPosOverlay);
//		overlays.add(myposOverlay);
//		sitioPosOverlay.setLocation(lat,lon);
//		myposOverlay.setLocation(loc);
//		myposOverlay.setNombre("Usted esta Aqui");
		
		mapa.setClickable(true);
		mapControl.setCenter(geoPointUbicacion);
		mapa.getOverlays().add(miPosicionOverlay);
		mapa.getOverlays().add(sitioOverlay);
		
	}
	
	private class BasicItemizedOverlay extends ItemizedOverlay<OverlayItem> {

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		
		public BasicItemizedOverlay(Drawable defaultMarker) {
			super(boundCenterBottom(defaultMarker));
			
		}
		
		public void addOverlay(OverlayItem overlay) {
            mOverlays.add(overlay);
            populate();
        }

		@Override
		protected OverlayItem createItem(int i) {
			return mOverlays.get(i);
		}

		@Override
		public int size() {
			return mOverlays.size();
		}
		
		@Override
        protected boolean onTap(int index) {
            return true;
        }
		
	}

}
