package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.PosicionGPS;
import com.uas.gsiam.utils.Util;

public class RadarActivity extends GDMapActivity{

	protected static final String TAG = "RadarActivity";
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
		
		if (loc != null){
			Log.i(TAG, "****************** El loc vino OK");
			
		}else{
			Log.i(TAG, "****************** El loc = null");
			loc = new Location("lala");
			loc.setLatitude(new Double(0));
			loc.setLongitude(new Double(0));
			
		}
		
		geoPointUbicacion = new GeoPoint(
				(int) (loc.getLatitude()*1000000),
				(int) (loc.getLongitude()*1000000));	
		mapa.setBuiltInZoomControls(true);		
		
		mapControl.setZoom(15);
		mapControl.animateTo(geoPointUbicacion);
		
		
		BasicItemizedOverlay miPosicionOverlay = new BasicItemizedOverlay(getResources().getDrawable(R.drawable.gd_map_pin_pin), this);
		miPosicionOverlay.addOverlay(new OverlayItem(geoPointUbicacion, "Aquí esoy", null));
		
		for (UsuarioDTO usuarioDTO : amigos) {
			
			// TODO : aqui falta filtar el tema de la hora de actualizacion!!!
			
			if (usuarioDTO.getPosicion() != null){
				
				GeoPoint geoPoint = new GeoPoint(
						(int) (usuarioDTO.getPosicion().getLat()*1000000),
						(int) (usuarioDTO.getPosicion().getLon()*1000000));
				
				Drawable drw;
				if (usuarioDTO.getAvatar() != null){
					Bitmap bit = Util.ArrayToBitmap(usuarioDTO.getAvatar());
					bit = Util.getResizedBitmap(bit, 50, 50);
					drw = Util.BitmapToDrawable(bit);
				}
				else{
					drw = getResources().getDrawable(R.drawable.avatardefault);
				}
				
				BasicItemizedOverlay amigoOverlay = new BasicItemizedOverlay(drw, this);
				
				Location locAmigo = new Location("Ubicacion Amigo");  
				locAmigo.setLatitude(usuarioDTO.getPosicion().getLat());  
				locAmigo.setLongitude(usuarioDTO.getPosicion().getLon());  
				Float distance = loc.distanceTo(locAmigo);  
				
				String fechaFormateada = new SimpleDateFormat("MM/dd/yyyy hh:mm").format(usuarioDTO.getPosicion().getFechaActualizacion());
				
				amigoOverlay.addOverlay(new OverlayItem(geoPoint, "Aquí se encuentra " + usuarioDTO.getNombre(), 
										"Ultima actualización: " + fechaFormateada +"\nDistancia: " + distance/1000 + "Km"));
				mapa.getOverlays().add(amigoOverlay);
			}
		}
		
		mapa.setClickable(true);
		mapControl.setCenter(geoPointUbicacion);
		mapa.getOverlays().add(miPosicionOverlay);
		
		
	}
	
	private class BasicItemizedOverlay extends ItemizedOverlay<OverlayItem> {

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context mContext;
		
		public BasicItemizedOverlay(Drawable defaultMarker, Context context) {
			super(boundCenterBottom(defaultMarker));
			 mContext = context;
			
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
			
			OverlayItem item = mOverlays.get(index);
			Util.showAlertDialog(mContext, item.getTitle(), item.getSnippet());
			
            return true;
        }
		
	}

}
