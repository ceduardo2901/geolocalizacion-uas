package com.uas.gsiam.utils;

import java.util.List;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

public class PosicionGPS {

	
	protected static Location loc;
	protected static LocationManager locationManager;
	
	
	public static Location getPosicion (Context contexto){
		
		locationManager = (LocationManager) contexto.getSystemService(Context.LOCATION_SERVICE);
		loc = getLastBestLocation(Constantes.MAX_DISTANCE,
				System.currentTimeMillis() - Constantes.MAX_TIME);
		
		return loc;
		
	}
	
	
	private static Location getLastBestLocation(int minDistance, long minTime) {
		Location bestResult = null;
		float bestAccuracy = Float.MAX_VALUE;
		long bestTime = Long.MIN_VALUE;

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
	
	
	
}
