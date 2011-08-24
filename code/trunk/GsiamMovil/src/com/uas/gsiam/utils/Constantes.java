package com.uas.gsiam.utils;

import android.app.AlarmManager;

public class Constantes {

	// radio por defecto.
	public static final int RADIO = 150;
	public static final String SHARED_PREFERENCE_FILE = "SHARED_PREFERENCE_FILE";
	public static final boolean SUPPORTS_GINGERBREAD = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;
	public static final boolean SUPPORTS_FROYO = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;
	// The maximum distance the user should travel between location updates.
	public static final int MAX_DISTANCE = RADIO / 2;
	// The maximum time that should pass before the user gets a location update.
	public static final long MAX_TIME = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	
	public final static String REGEX_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	
	public static final String RETURN_OK = "ok";
	
	// Servicios
	public static final String LOGIN_SERVICE_URL = "http://10.0.2.2:8080/GsiamWeb2/usuarios/login/{email}/{pass}";
	public static final String LOGIN_FILTRO_ACTION = "com.gsiam.places.LOGIN_FILTRO_ACTION";
	
	public static final String CREAR_USUARIO_SERVICE_URL = "http://10.0.2.2:8080/GsiamWeb2/usuarios/agregar";
	public static final String CREAR_USUARIO_FILTRO_ACTION = "com.gsiam.places.CREAR_USUARIO_FILTRO_ACTION";
	
	// Mensajes 
	public static final String MSG_ESPERA_GENERICO = "Cargando. Por favor espere..."; 
	
	public static final String MSG_ERROR_MAIL = "El email es invalido, igrese uno correcto";
	
	
	
	
}
