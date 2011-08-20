package com.uas.gsiam.utils;

import android.app.AlarmManager;

public class Constantes {

	// radio por defecto.
	public static int RADIO = 150;
	public static String SHARED_PREFERENCE_FILE = "SHARED_PREFERENCE_FILE";
	public static boolean SUPPORTS_GINGERBREAD = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;
	public static boolean SUPPORTS_FROYO = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;
	// The maximum distance the user should travel between location updates.
	public static int MAX_DISTANCE = RADIO / 2;
	// The maximum time that should pass before the user gets a location update.
	public static long MAX_TIME = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	
	public static String LOGIN_SERVICE_URL = "http://10.0.2.2:8080/GsiamWeb2/usuarios/login/{email}/{pass}";
}
