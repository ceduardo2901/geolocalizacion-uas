package com.uas.gsiam.utils;

import android.app.AlarmManager;

public class Constantes {

	// radio por defecto.
	public static final int RADIO = 150;
	public static String METROS = "metros";
	public static final String SHARED_PREFERENCE_FILE = "SHARED_PREFERENCE_FILE";
	public static final boolean SUPPORTS_GINGERBREAD = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.GINGERBREAD;
	public static final boolean SUPPORTS_FROYO = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO;
	// The maximum distance the user should travel between location updates.
	public static final int MAX_DISTANCE = RADIO / 2;
	// The maximum time that should pass before the user gets a location update.
	public static final long MAX_TIME = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
	
	public final static String REGEX_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
	
	public static final String RETURN_OK = "ok";
	
	public static final int REQUEST_CAMERA = 1;
	public static final int REQUEST_SELECT_PHOTO = 2;
	
	// Servicios
	
	//TODO poner esto en un archivo de configuracion
	public static final String DOMINIO_SERVICE = "http://10.0.2.2:8080";
	
	public static final String LOGIN_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/usuarios/login/{email}/{pass}";
	public static final String LOGIN_FILTRO_ACTION = "com.gsiam.places.LOGIN_FILTRO_ACTION";
	
	public static final String CREAR_USUARIO_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/usuarios/agregar";
	public static final String CREAR_USUARIO_FILTRO_ACTION = "com.gsiam.places.CREAR_USUARIO_FILTRO_ACTION";
	
	public static final String EDITAR_USUARIO_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/usuarios/editar";
	public static final String EDITAR_USUARIO_FILTRO_ACTION = "com.gsiam.places.EDITAR_USUARIO_FILTRO_ACTION";
	
	public static final String SITIOS_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/sitios/{lat}/{lon}";
	public static final String BUSQUEDA_SITIOS_SERVICE_URL = DOMINIO_SERVICE + "http://10.0.2.2:8080/GsiamWeb2/sitios/{nombre}";
	public static final String SITIO_FILTRO_ACTION = "com.gsiam.places.SITIO_FILTRO_ACTION";
	
	public static final String CREAR_SITIOS_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/sitios/agregar";
	public static final String CREAR_SITIO_FILTRO_ACTION = "com.gsiam.places.CREAR_SITIO_FILTRO_ACTION";
	
	public static final String ELIMINAR_SITIOS_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/sitios/eliminar/{sitio}";
	public static final String ELIMINAR_SITIO_FILTRO_ACTION = "com.gsiam.places.ELIMINAR_SITIO_FILTRO_ACTION";
	
	public static final String MODIFICAR_SITIOS_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/sitios/modificar";
	public static final String MODIFICAR_SITIO_FILTRO_ACTION = "com.gsiam.places.MODIFICAR_SITIO_FILTRO_ACTION";
	
	public static final String CREAR_PUBLICACION_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/sitios/publicar";
	public static final String CREAR_PUBLICACION_FILTRO_ACTION = "com.gsiam.places.CREAR_PUBLICACION_FILTRO_ACTION";
	
	public static final String GET_AMIGOS_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/usuarios/amigos/{id}";
	public static final String GET_AMIGOS_FILTRO_ACTION = "com.gsiam.places.GET_AMIGOS_FILTRO_ACTION";
	
	public static final String GET_USUARIOS_SERVICE_URL = DOMINIO_SERVICE + "/GsiamWeb2/usuarios/usuarios/{nombre}";
	public static final String GET_USUARIOS_FILTRO_ACTION = "com.gsiam.places.GET_USUARIOS_FILTRO_ACTION";
	
	
	// Mensajes 
	public static final String MSG_ESPERA_GENERICO = "Conectando con el servidor, por favor espere..."; 
	public static final String MSG_ESPERA_INICIANDO_SESION = "Iniciando sesion, por favor espere...";
	public static final String MSG_ESPERA_BUSCANDO = "Buscando, por favor espere...";
	public static final String MSG_USUARIO_CREADO_OK = "El usuario se ha creado exitosamente";
	public static final String MSG_USUARIO_EDITADO_OK = "Perfil actualizado exitosamente";
	public static final String MSG_ERROR_MAIL = "El email es invalido, igrese uno correcto";
	public static final String MSG_ERROR_SERVIDOR = "Error al intentar acceder al servidor";
	public static final String MSG_GPS_DISABLE = "Por favor habilite su gps para continuar";
	public static final String MSG_ACEPTAR = "Aceptar";
	public static final String MSG_CANCELAR = "Cancelar";
	
	
	
}
