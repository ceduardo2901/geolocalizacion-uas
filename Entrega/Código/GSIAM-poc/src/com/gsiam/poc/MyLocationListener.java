package com.gsiam.poc;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

class MyLocationListener implements LocationListener {
	
	Handler handler;
	private Location location;
	
	public void onLocationChanged(Location loc) {
		if (loc != null) {
			location = loc;
			handler.sendEmptyMessage(0);
		}
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	
	public void setHandler(Handler h){
		handler = h;
	}

	

}
