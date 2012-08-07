package com.uas.gsiam.utils;

/**
 * Constantes de la aplicacion movil
 * 
 * @author Antonio
 * 
 */
public class Constantes {

	// radio por defecto.
	public static final int RADIO = 150;
	public static final int TIMEOUT = 20000;
	public static final int ACTUALIZAR_POSICION = 60000;
	public static String METROS = "metros";
	public static final String SHARED_PREFERENCE_FILE = "SHARED_PREFERENCE_FILE";

	public final static String REGEX_EMAIL = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

	public static final int REQUEST_CAMERA = 1;
	public static final int REQUEST_SELECT_PHOTO = 2;

	public static final String ACEPTAR_SOLICITUD = "ACEPTAR";
	public static final String RECHAZAR_SOLICITUD = "RECHAZAR";

	// Servicios

	// TODO poner esto en un archivo de configuracion
	 public static final String DOMINIO_SERVICE = "http://10.0.2.2:8080";
	// ip tony
	//public static final String DOMINIO_SERVICE = "http://gsiam.servehttp.com:8080";
	// ipmartin
	// public static final String DOMINIO_SERVICE =
	// "http://gsiamweb.no-ip.info:8080";
	public static final String LOGIN_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/login/{email}/{pass}";
	public static final String LOGIN_FILTRO_ACTION = "com.gsiam.places.LOGIN_FILTRO_ACTION";

	public static final String CREAR_USUARIO_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/agregar";
	public static final String CREAR_USUARIO_FILTRO_ACTION = "com.gsiam.places.CREAR_USUARIO_FILTRO_ACTION";

	public static final String MODIFICAR_USUARIO_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/modificar";
	public static final String MODIFICAR_USUARIO_FILTRO_ACTION = "com.gsiam.places.MODIFICAR_USUARIO_FILTRO_ACTION";

	public static final String CERRAR_CUENTA_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/cerrar";
	public static final String CERRAR_CUENTA_FILTRO_ACTION = "com.gsiam.places.CERRAR_CUENTA_FILTRO_ACTION";

	public static final String SITIOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/{lat}/{lon}";
	public static final String BUSQUEDA_SITIOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/sitio/{id}/{nombre}/{lat}/{lon}";
	public static final String SITIO_FILTRO_ACTION = "com.gsiam.places.SITIO_FILTRO_ACTION";

	public static final String CREAR_SITIOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/agregar";
	public static final String CREAR_SITIO_FILTRO_ACTION = "com.gsiam.places.CREAR_SITIO_FILTRO_ACTION";

	public static final String ELIMINAR_SITIOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/eliminar/{usuario}/{sitio}";
	public static final String ELIMINAR_SITIO_FILTRO_ACTION = "com.gsiam.places.ELIMINAR_SITIO_FILTRO_ACTION";

	public static final String MODIFICAR_SITIOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/modificar";
	public static final String MODIFICAR_SITIO_FILTRO_ACTION = "com.gsiam.places.MODIFICAR_SITIO_FILTRO_ACTION";

	public static final String CREAR_PUBLICACION_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/publicar";
	public static final String CREAR_PUBLICACION_FILTRO_ACTION = "com.gsiam.places.CREAR_PUBLICACION_FILTRO_ACTION";

	public static final String GET_AMIGOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/amigos/{id}";
	public static final String GET_AMIGOS_FILTRO_ACTION = "com.gsiam.places.GET_AMIGOS_FILTRO_ACTION";

	public static final String GET_USUARIOS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/usuarios/{id}/{nombre}";
	public static final String GET_USUARIOS_FILTRO_ACTION = "com.gsiam.places.GET_USUARIOS_FILTRO_ACTION";

	public static final String CREAR_SOLICITUD_AMISTAD_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/agregarsolicitud";
	public static final String CREAR_SOLICITUD_AMISTAD_FILTRO_ACTION = "com.gsiam.places.CREAR_SOLICITUD_AMISTAD_FILTRO_ACTION";

	public static final String GET_SOLICITUDES_RECIBIDAS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/solicitudesrecibidas/{id}";
	public static final String GET_SOLICITUDES_ENVIADAS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/solicitudesenviadas/{id}";

	public static final String GET_SOLICITUDES_FILTRO_ACTION = "com.gsiam.places.GET_SOLICITUDES_FILTRO_ACTION";

	public static final String ACEPTAR_SOLICITUD_AMISTAD_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/aceptarsolicitud";
	public static final String RECHAZAR_SOLICITUD_AMISTAD_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/rechazarsolicitud";
	public static final String RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION = "com.gsiam.places.RESPONDER_SOLICITUD_AMISTAD_FILTRO_ACTION";

	public static final String ENVIAR_INVITACIONES_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/invitar/{email}/{nombre}";
	public static final String ENVIAR_INVITACIONES_FILTRO_ACTION = "com.gsiam.places.ENVIAR_INVITACIONES_FILTRO_ACTION";

	public static final String ACTUALIZAR_POSICION_USUARIO_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/usuarios/actualizarPosicion";
	public static final String ACTUALIZAR_POSICION_USUARIO_FILTRO_ACTION = "com.gsiam.places.ACTUALIZAR_POSICION_USUARIO_FILTRO_ACTION";

	public static final String GET_CATEGORIAS_SERVICE_URL = DOMINIO_SERVICE
			+ "/GsiamWeb2/sitios/categorias";
	public static final String GET_CATEGORIAS_FILTRO_ACTION = "com.gsiam.places.GET_CATEGORIAS_FILTRO_ACTION";

	// Mensajes
	public static final String MSG_ESPERA_GENERICO = "Conectando con el servidor, por favor espere...";
	public static final String MSG_ESPERA_INICIANDO_SESION = "Iniciando sesion, por favor espere...";
	public static final String MSG_ESPERA_BUSCANDO = "Buscando, por favor espere...";
	public static final String MSG_ESPERA_ACTUALIZANDO = "Actualizando Datos, por favor espere...";
	public static final String MSG_ESPERA_ENVIANDO_SOLICITUD = "Enviando solicitud, por favor espere...";
	public static final String MSG_ESPERA_ACEPTANDO_SOLICITUD = "Aceptando solicitud, por favor espere...";
	public static final String MSG_ESPERA_RECHAZANDO_SOLICITUD = "Rechazando solicitud, por favor espere...";
	public static final String MSG_ESPERA_ENVIANDO_INVITACION = "Enviando invitacion, por favor espere...";
	public static final String MSG_ESPERA_DETALLE_SITIO = "Obteniendo informacion, por favor espere...";
	public static final String MSG_USUARIO_CREADO_OK = "El usuario se ha \ncreado exitosamente";
	public static final String MSG_CONFIRMAR_MODIFICACION_USUARIO = "¿Esta seguro que desea modificar su perfil?";
	public static final String MSG_CONFIRMAR_CIERRE_CUENTA = "¿Esta seguro que desea cerrar su cuenta?";
	public static final String MSG_CONFIRMAR_MODIFICAR_SITIO = "¿Esta seguro que desea modificar el sitio?";
	public static final String MSG_CONFIRMAR_ELIMINACION_SITIO = "¿Esta seguro que desea eliminar ";
	public static final String MSG_SOLICITUD_CREADA_OK = "La solicitud se envio correctamente";
	public static final String MSG_SOLICITUD_APROBADA = "La solicitud se aprobo correctamente";
	public static final String MSG_SOLICITUD_RECHAZADA = "La solicitud se rechazo correctamente";
	public static final String MSG_USUARIO_EDITADO_OK = "Perfil actualizado exitosamente";
	public static final String MSG_CUENTA_CERRADA_OK = "Su cuenta se ha \ndado de baja existosamente";
	public static final String MSG_ERROR_MAIL = "El email es invalido, igrese uno correcto";
	public static final String MSG_GPS_DISABLE = "Para continuar habilite el gps";
	public static final String MSG_ACEPTAR = "Aceptar";
	public static final String MSG_CANCELAR = "Cancelar";
	public static final String MSG_LOGIN_ERROR = "El email o password incorrecto";
	public static final String MSG_MENSAJE_ENVIADO = "El mensaje se envio correctamente";
	public static final String MSG_PUBLICACION_CREADA = "La publicacion se creo correctamente";
	public static final String MSG_NO_EXISTEN_SITIOS_POR_NOMBRE = "No se encontraron sitios con el nombre ingresado";
	public static final String MSG_SITIO_CREADO = "El Sitio se creo correctamente";
	public static final String MSG_INVITACIONES_OK = "Invitacion enviada existosamente";
	public static final String MSG_CONEXION_ERROR = "Para continuar debe tener conexion a internet";
	public static final String MSG_INVITACIONES_FACEBOOK = " quiere que te unas a la red Gsiam";
	public static final String MSG_INVITACIONES_FACEBOOK_OK = "Se envio la invitación de forma correcta";
	public static final String MSG_INVITACIONES_FACEBOOK_ERROR = "Error al enviar solicitud, intentelo mas tarde";
	public static final String MSG_INVITACIONES_FACEBOOK_SELECCION = "Debe seleccionar al menos un contacto para invitar";
	public static final String MSG_CREAR_SITIO_OK = "El sitio se creo correctamente";
	public static final String MSG_MODIFICAR_SITIO_OK = "El sitio se modifico correctamente";
	public static final String MSG_ELIMINAR_SITIO_OK = "El sitio se elimino correctamente";
	public static final String MSG_RADAR_TAB_AMIGOS = "Para ver el radar vaya sobre la pestaña amigos";
	public static final String MSG_ERROR_INESPERADO = "Ups!!! ocurrio un error inesperado, intentelo luego";
	public static final String MSG_ERROR_TIMEOUT = "Error al acceder al servidor, intentelo luego";
	public static final String MSG_CAMPOS_OBLIGATORIOS = "El campo nombre y/o dirección son obligatorios";
	public static final String MSG_NO_EXISTEN_SITIOS = "No se encontraron sitios cercanos a su ubicación";
}
