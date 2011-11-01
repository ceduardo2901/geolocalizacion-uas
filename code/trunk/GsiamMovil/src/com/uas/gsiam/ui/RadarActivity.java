package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.PosicionGPS;
import com.uas.gsiam.utils.Util;

public class RadarActivity extends GDMapActivity{

	
	private MapView mapa;
	private GeoPoint geoPointUbicacion;
	private Location loc;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.radar);
		mapa = (MapView) findViewById(R.id.radarid);
		
		
		loc = PosicionGPS.getPosicion(getApplicationContext());
		mostrarMapa(MisAmigosActivity.misAmigos);
		
		inicializarBar();
		
	}
	
	private void inicializarBar() {
		
		getActionBar().setTitle("GSIAM - Radar Amigos");
	}
	
	protected void onResume(){
		super.onResume();

	}
	
	
	@Override
	protected boolean isRouteDisplayed() {
		
		return false;
	}
	
	private void mostrarMapa(ArrayList<UsuarioDTO> amigos) {

		if (mapa == null)
			mapa = (MapView) findViewById(R.id.radarid);
		MapController mapControl = mapa.getController();
		
		geoPointUbicacion = new GeoPoint(
				(int) (loc.getLatitude()*1000000),
				(int) (loc.getLongitude()*1000000));
		mapa.setBuiltInZoomControls(true);		
		
		mapControl.setZoom(15);
		mapControl.animateTo(geoPointUbicacion);
		
		
		BasicItemizedOverlay miPosicionOverlay = new BasicItemizedOverlay(getResources().getDrawable(R.drawable.gd_map_pin_pin));
		miPosicionOverlay.addOverlay(new OverlayItem(geoPointUbicacion, "lalala", "null"));
		
		for (UsuarioDTO usuarioDTO : amigos) {
			
			if (usuarioDTO.getPosicion() != null){
				
				GeoPoint geoPoint = new GeoPoint(
						(int) (usuarioDTO.getPosicion().getLat()*1000000),
						(int) (usuarioDTO.getPosicion().getLon()*1000000));
				
				Bitmap bit = Util.ArrayToBitmap(usuarioDTO.getAvatar());
				bit = Util.getResizedBitmap(bit, 50, 50);
				Drawable drw = Util.BitmapToDrawable(bit);
				
				BasicItemizedOverlay amigoOverlay = new BasicItemizedOverlay(drw);
				amigoOverlay.addOverlay(new OverlayItem(geoPoint, usuarioDTO.getNombre(), usuarioDTO.getNombre()));
				mapa.getOverlays().add(amigoOverlay);
			}
		}
		
		mapa.setClickable(true);
		mapControl.setCenter(geoPointUbicacion);
		mapa.getOverlays().add(miPosicionOverlay);
		
		
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
