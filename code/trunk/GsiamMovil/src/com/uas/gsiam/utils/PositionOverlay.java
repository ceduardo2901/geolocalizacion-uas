package com.uas.gsiam.utils;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.location.Location;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class PositionOverlay extends Overlay {

	private final int mRadius = 5;
	private Location location;
	private Double lat;
	private Double lon;
	private String nombre;

	public void setLocation(Location loc) {
		location = loc;

	}

	public void setLocation(Double lat, double lon) {
		this.lat = lat;
		this.lon = lon;

	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		GeoPoint geoPoint;
		Paint paint = new Paint();
		if (location != null) {
			geoPoint = new GeoPoint((int) (location.getLatitude() * 1000000),
					(int) (location.getLongitude() * 1000000));
			paint.setColor(Color.BLUE);
		} else {
			geoPoint = new GeoPoint((int) (lat * 1000000),
					(int) (lon * 1000000));
			paint.setColor(Color.RED);
		}

		Point point = new Point();
		Projection projection = mapView.getProjection();
		projection.toPixels(geoPoint, point);
		RectF oval = new RectF(point.x - mRadius, point.y - mRadius, point.x
				+ mRadius, point.y + mRadius);
		
		//paint.setARGB(250, 255, 0, 0);
		paint.setAntiAlias(true);
		paint.setFakeBoldText(true);
		Paint backPaint = new Paint();
		backPaint.setARGB(175, 0, 0, 0);
		backPaint.setAntiAlias(true);
		String text = "";
		if (nombre != null) {
			text = nombre;
//			RectF backRect = new RectF(point.x + 2 + mRadius, point.y - 3
//					* mRadius, point.x + 60, point.y + mRadius);
//			canvas.drawRoundRect(backRect, 5, 5, backPaint);
			canvas.drawText(text, point.x + 2 * mRadius, point.y, paint);
			canvas.drawPoint(point.x, point.y, paint);
		} 

			canvas.drawOval(oval, paint);
		

	}
}
