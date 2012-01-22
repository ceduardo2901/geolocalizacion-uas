package com.uas.gsiam.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Clase encargada de obtener la ubicación geografica del dispositivo movil
 * 
 * @author Antonio
 * 
 */
public class LocationHelper {

	LocationManager locationManager;
	private LocationResult locationResult;
	boolean gpsEnabled = false;
	boolean networkEnabled = false;

	/**
	 * Retorna la ubicacion geografica en terminos de latitud y longitud del
	 * dispositivo movil
	 * 
	 * @param context
	 *            Contexto de la aplicación
	 * @param result
	 *            Retorna la ubicacion en un objeto {@link LocationResult}
	 * @return Retorna true si se pudo obtener la ubicacion geografica, false en
	 *         caso contrario
	 */
	public boolean getLocation(Context context, LocationResult result) {
		locationResult = result;

		if (locationManager == null) {
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
		}
		// la excepccion es lanzada si el provedor no esta habilitado
		try {
			gpsEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);
		} catch (Exception ex) {
		}
		try {
			networkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		} catch (Exception ex) {
		}

		// no inicio el listener si el provider no esta habilitado
		if (!gpsEnabled && !networkEnabled) {
			return false;
		}

		if (gpsEnabled) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		}
		if (networkEnabled) {

			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					locationListenerNetwork);
		}

		GetLastLocation();
		return true;
	}

	/**
	 * Implementacion de un locationListener para escuchar los cambios en la
	 * ubicación
	 */
	LocationListener locationListenerGps = new LocationListener() {
		public void onLocationChanged(Location location) {
			locationResult.obtenerUbicacion(location);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerNetwork);

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extra) {
		}
	};

	/**
	 * Implementacion de un locationListener para escuchar los cambios en la
	 * ubicación
	 */
	LocationListener locationListenerNetwork = new LocationListener() {
		public void onLocationChanged(Location location) {
			locationResult.obtenerUbicacion(location);
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerGps);

		}

		public void onProviderDisabled(String provider) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onStatusChanged(String provider, int status, Bundle extra) {
		}

	};

	/**
	 * Obtiene la ubicacion geografica del movil y la almacena en un objeto
	 * {@link LocationResult}
	 */
	private void GetLastLocation() {
		locationManager.removeUpdates(locationListenerGps);
		locationManager.removeUpdates(locationListenerNetwork);

		Location gpsLocation = null;
		Location networkLocation = null;

		if (gpsEnabled) {
			gpsLocation = locationManager
					.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		if (networkEnabled) {
			networkLocation = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}

		// Si hay dos valores utilizo el mas reciente
		if (gpsLocation != null && networkLocation != null) {
			if (gpsLocation.getTime() > networkLocation.getTime()) {
				locationResult.obtenerUbicacion(gpsLocation);
			} else {
				locationResult.obtenerUbicacion(networkLocation);
			}

			return;
		}

		if (gpsLocation != null) {
			locationResult.obtenerUbicacion(gpsLocation);
			return;
		}

		if (networkLocation != null) {
			locationResult.obtenerUbicacion(networkLocation);
			return;
		}

		locationResult.obtenerUbicacion(null);
	}

	public static abstract class LocationResult {
		public abstract void obtenerUbicacion(Location location);
	}

	/**
	 * Metodo que elimina los listener registrados para escuchar los cambios de
	 * ubicación
	 */
	public void stopLocationUpdates() {
		locationManager.removeUpdates(locationListenerGps);
		locationManager.removeUpdates(locationListenerNetwork);
	}
}
