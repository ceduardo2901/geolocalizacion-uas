package com.gsiam.poc;

import android.graphics.Canvas;
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
	private Location myLocation;

	public void setLocation(Location loc) {
		myLocation = loc;

	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		GeoPoint geoPoint = new GeoPoint(
				(int) (myLocation.getLatitude() * 1000000),
				(int) (myLocation.getLongitude() * 1000000));
		Point point = new Point();
		Projection projection = mapView.getProjection();
		projection.toPixels(geoPoint, point);
		RectF oval = new RectF(point.x - mRadius, point.y - mRadius, point.x
				+ mRadius, point.y + mRadius);
		Paint paint = new Paint();
		paint.setARGB(250, 255, 0, 0);
		paint.setAntiAlias(true);
		paint.setFakeBoldText(true);
		Paint backPaint = new Paint();
		backPaint.setARGB(175, 0, 0, 0);
		backPaint.setAntiAlias(true);
		String text = "Tony";
		RectF backRect = new RectF(point.x + 2 + mRadius,
				point.y - 3 * mRadius, point.x + 60, point.y + mRadius);
		canvas.drawOval(oval, paint);
		canvas.drawRoundRect(backRect, 5, 5, backPaint);
		canvas.drawText(text, point.x + 2 * mRadius, point.y, paint);
	}
}
