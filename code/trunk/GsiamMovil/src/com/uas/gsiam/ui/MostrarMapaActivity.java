package com.uas.gsiam.ui;

import greendroid.app.GDMapActivity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.uas.gsiam.utils.ApplicationController;
import com.uas.gsiam.utils.LocationHelper;
import com.uas.gsiam.utils.Util;
import com.uas.gsiam.utils.LocationHelper.LocationResult;

/**
 * 
 * Muestra el mapa junto a la posicion del usuario y la del punto de interes
 * seleccionado.
 * 
 * @author Antonio
 * 
 */
public class MostrarMapaActivity extends GDMapActivity {

	private MapView mapa;
	/**
	 * ubicacion geografica del punto de interes
	 */
	private GeoPoint geoPoint;
	/**
	 * ubicacion geografica del usuario
	 */
	private GeoPoint geoPointUbicacion;
	private Location loc;
	private LocationHelper locHelper;
	protected ApplicationController app;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setActionBarContentView(R.layout.mostrar_mapa);
		mapa = (MapView) findViewById(R.id.mapaId);
		app = ((ApplicationController) getApplicationContext());
		locHelper = new LocationHelper();
		locHelper.getLocation(this, locationResult);

		Double lat = new Double(0);
		Double lon = new Double(0);
		if (SitioTabActivity.sitio != null) {
			lat = SitioTabActivity.sitio.getLat();
			lon = SitioTabActivity.sitio.getLon();
		}

		mostrarMapa(lat, lon);

		inicializarBar();

	}

	public LocationResult locationResult = new LocationResult() {

		@Override
		public void obtenerUbicacion(final Location location) {
			loc = location;
		}
	};

	private void inicializarBar() {

		setTitle(R.string.app_name);
	}

	protected void onResume() {
		super.onResume();

	}

	@Override
	protected boolean isRouteDisplayed() {

		return false;
	}

	/**
	 * Muestra el mapa junto a los dos puntos en el mapa. El punto de interes
	 * seleccionado y la ubicacion del usuario
	 * 
	 * @param lat
	 *            Latitud del sitio
	 * @param lon
	 *            Longuitud del sitio
	 */
	private void mostrarMapa(Double lat, Double lon) {

		if (mapa == null)
			mapa = (MapView) findViewById(R.id.mapaId);
		MapController mapControl = mapa.getController();
		geoPoint = new GeoPoint((int) (lat * 1000000), (int) (lon * 1000000));
		geoPointUbicacion = new GeoPoint((int) (loc.getLatitude() * 1000000),
				(int) (loc.getLongitude() * 1000000));
		mapa.setBuiltInZoomControls(true);
		mapa.getZoomButtonsController().setAutoDismissed(false);
		mapa.getZoomButtonsController().setVisible(true);
		
		int latSpan = 0;
		int lonSpan = 0;

		BasicItemizedOverlay miPosicionOverlay = new BasicItemizedOverlay(
				getResources().getDrawable(R.drawable.gd_map_pin_pin), this);

		BasicItemizedOverlay sitioOverlay = new BasicItemizedOverlay(
				getResources().getDrawable(R.drawable.gd_map_pin_base), this);
		miPosicionOverlay.addOverlay(new OverlayItem(geoPointUbicacion, app
				.getUserLogin().getNombre(), app.getUserLogin().getNombre()));
		sitioOverlay.addOverlay(new OverlayItem(geoPoint,
				SitioTabActivity.sitio.getNombre(), SitioTabActivity.sitio
						.getNombre()));
		
		mapa.setClickable(true);
		mapa.displayZoomControls(true);

		mapa.getOverlays().add(miPosicionOverlay);
		mapa.getOverlays().add(sitioOverlay);

		int minLatitude = Integer.MAX_VALUE;
		int maxLatitude = Integer.MIN_VALUE;
		int minLongitude = Integer.MAX_VALUE;
		int maxLongitude = Integer.MIN_VALUE;

		if (geoPoint.getLatitudeE6() > geoPointUbicacion.getLatitudeE6()) {
			maxLatitude = geoPoint.getLatitudeE6();
			minLatitude = geoPointUbicacion.getLatitudeE6();

		} else {
			maxLatitude = geoPointUbicacion.getLatitudeE6();
			minLatitude = geoPoint.getLatitudeE6();

		}
		if (geoPoint.getLongitudeE6() > geoPointUbicacion.getLongitudeE6()) {
			maxLongitude = geoPoint.getLongitudeE6();
			minLongitude = geoPointUbicacion.getLongitudeE6();

		} else {
			maxLongitude = geoPointUbicacion.getLongitudeE6();
			minLongitude = geoPoint.getLongitudeE6();

		}

		mapControl.zoomToSpan(Math.abs(maxLatitude - minLatitude),
				Math.abs(maxLongitude - minLongitude));

		mapControl.animateTo(new GeoPoint((maxLatitude + minLatitude) / 2,
				(maxLongitude + minLongitude) / 2));

	}

	private class BasicItemizedOverlay extends ItemizedOverlay<OverlayItem> {

		private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
		private Context context;

		public BasicItemizedOverlay(Drawable defaultMarker, Context context) {
			super(boundCenterBottom(defaultMarker));
			this.context = context;

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
			
			Util.showAlertDialog(context, item.getTitle(), item.getSnippet());

			return true;
		}

	}

}
