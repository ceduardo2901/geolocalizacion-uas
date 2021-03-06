package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.utils.Constantes;
import com.uas.gsiam.utils.LocationHelper;
import com.uas.gsiam.utils.LocationHelper.LocationResult;
import com.uas.gsiam.utils.Util;

/**
 * 
 * Esta activity muestra un mapa con los amigos del usuario que comparte su
 * ubicacion.
 * 
 * @author Antonio
 * 
 */
public class RadarActivity extends GDMapActivity implements LocationListener {

	protected static final String TAG = "RadarActivity";
	private MapView mapa;
	private GeoPoint geoPointUbicacion;
	private Location loc;
	private LocationHelper locHelper;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.radar);
		mapa = (MapView) findViewById(R.id.radarid);

		locHelper = new LocationHelper();
		locHelper.getLocation(this, locationResult);

		mostrarMapa(MisAmigosActivity.misAmigos);

		inicializarBar();

	}

	public LocationResult locationResult = new LocationResult() {

		@Override
		public void obtenerUbicacion(final Location location) {
			loc = location;
		}
	};

	private void inicializarBar() {

		getActionBar().setTitle(getString(R.string.radar));
	}

	protected void onResume() {
		super.onResume();

	}

	@Override
	protected boolean isRouteDisplayed() {

		return false;
	}

	/**
	 * Este metodo desplegara la posicion del usuario y todos los amigos que
	 * compartan su ubicacion en este momento
	 * 
	 * @param amigos
	 *            Lista de amigos a desplegar sobre el mapa.
	 */
	private void mostrarMapa(ArrayList<UsuarioDTO> amigos) {

		if (mapa == null)
			mapa = (MapView) findViewById(R.id.radarid);
		MapController mapControl = mapa.getController();
		mapa.setBuiltInZoomControls(true);
		mapControl.setZoom(15);
		mapa.setClickable(true);

		if (loc == null) {
			Util.showToast(this, Constantes.MSG_GPS_DISABLE);

		} else {
			geoPointUbicacion = new GeoPoint(
					(int) (loc.getLatitude() * 1000000),
					(int) (loc.getLongitude() * 1000000));

			mapControl.animateTo(geoPointUbicacion);

			BasicItemizedOverlay miPosicionOverlay = new BasicItemizedOverlay(
					getResources().getDrawable(R.drawable.gd_map_pin_pin), this);
			miPosicionOverlay.addOverlay(new OverlayItem(geoPointUbicacion,
					"Aqu� esoy", null));
			
			Float distanciaMasCercana = Float.MAX_VALUE;
			GeoPoint geoPointMasCercano = null; 
			boolean hayAmigos = false;
			
			for (UsuarioDTO usuarioDTO : amigos) {

				// TODO : se podria agregar el tema de la hora de actualizacion en caso de ser necesario
	
				if (usuarioDTO.getPosicion() != null) {

					GeoPoint geoPoint = new GeoPoint((int) (usuarioDTO
							.getPosicion().getLat() * 1000000),
							(int) (usuarioDTO.getPosicion().getLon() * 1000000));

					Drawable drw;
					if (usuarioDTO.getAvatar() != null) {
						Bitmap bit = Util.arrayToBitmap(usuarioDTO.getAvatar());
						bit = Util.getResizedBitmap(bit, 40, 40);
						drw = Util.bitmapToDrawable(bit);
					} else {
						drw = getResources().getDrawable(
								R.drawable.avatardefault);
					}

					BasicItemizedOverlay amigoOverlay = new BasicItemizedOverlay(
							drw, this);

					Location locAmigo = new Location("Ubicacion Amigo");
					locAmigo.setLatitude(usuarioDTO.getPosicion().getLat());
					locAmigo.setLongitude(usuarioDTO.getPosicion().getLon());
					
					Float distancia = loc.distanceTo(locAmigo);
					
					if (distancia < distanciaMasCercana){
						distanciaMasCercana = distancia;
						geoPointMasCercano = geoPoint;
					}
					
					String distanciaStr = null;
					
					// paso a kilometros
					distancia = distancia / 1000; 
					Log.i(TAG, "Distancia=" + distancia);
					
					String unidad = " km";
					if (distancia < 1){
						unidad = " mt";
						DecimalFormat df = new DecimalFormat("0");
						distanciaStr = df.format(distancia * 1000);
						
					}else{
						DecimalFormat df = new DecimalFormat("0.0");
						distanciaStr = df.format(distancia);
					}
					
					
					String fechaFormateada = new SimpleDateFormat("MM/dd/yyyy hh:mm").format(usuarioDTO.getPosicion().getFechaActualizacion());

					amigoOverlay.addOverlay(new OverlayItem(geoPoint,
									"Aqu� se encuentra " + usuarioDTO.getNombre(),
									"Ultima actualizaci�n: " + fechaFormateada + "\nDistancia: " + distanciaStr + unidad));
					mapa.getOverlays().add(amigoOverlay);
					hayAmigos = true;
					
				}
				
			}

			mapControl.setCenter(geoPointUbicacion);
			mapa.getOverlays().add(miPosicionOverlay);
		
			if (geoPointMasCercano != null){
				
				int minLatitude = Integer.MAX_VALUE;
				int maxLatitude = Integer.MIN_VALUE;
				int minLongitude = Integer.MAX_VALUE;
				int maxLongitude = Integer.MIN_VALUE;

				if (geoPointMasCercano.getLatitudeE6() > geoPointUbicacion.getLatitudeE6()) {
					maxLatitude = geoPointMasCercano.getLatitudeE6();
					minLatitude = geoPointUbicacion.getLatitudeE6();

				} else {
					maxLatitude = geoPointUbicacion.getLatitudeE6();
					minLatitude = geoPointMasCercano.getLatitudeE6();

				}
				if (geoPointMasCercano.getLongitudeE6() > geoPointUbicacion.getLongitudeE6()) {
					maxLongitude = geoPointMasCercano.getLongitudeE6();
					minLongitude = geoPointUbicacion.getLongitudeE6();

				} else {
					maxLongitude = geoPointUbicacion.getLongitudeE6();
					minLongitude = geoPointMasCercano.getLongitudeE6();

				}

				mapControl.zoomToSpan(Math.abs(maxLatitude - minLatitude),
						Math.abs(maxLongitude - minLongitude));

				mapControl.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2,
						(maxLongitude + minLongitude) / 2));
			}
			
			
			if(!hayAmigos){
				Util.showToast(this, "Ninguno de sus amigos comparte su ubicaci�n");
			}
			
		}

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

	@Override
	public void onLocationChanged(Location location) {
		this.loc = location;

	}

	@Override
	public void onProviderDisabled(String arg0) {
		Util.showToast(getApplicationContext(), "EL gps no esta encendido");

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}
