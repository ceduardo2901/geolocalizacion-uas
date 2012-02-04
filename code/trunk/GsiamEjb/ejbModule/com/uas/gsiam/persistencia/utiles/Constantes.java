package com.uas.gsiam.persistencia.utiles;

/**
 * 
 * Constantes del sistema
 * 
 * @author Martín
 * 
 */
public class Constantes {

	public static final int ACEPTAR_SOLICITUD = 0;
	public static final int RECHAZAR_SOLICITUD = 1;

	// Mensajes de Error
	public static String ERROR_COMUNICACION_BD = "Error al comunicarse con la Base de Datos";
	public static String ERROR_CREAR_USUARIO = "Error al intentar crear el usuario";
	public static String ERROR_MODIFICAR_USUARIO = "Error al intentar modificar el usuario";
	public static String ERROR_CERRAR_CUENTA = "Error al intentar cerrar su cuenta";
	public static String ERROR_CREAR_CONTACTO = "Error al intentar crear la solicitud";
	public static String ERROR_RECUPERAR_SOLICITUDES = "Error al intentar traer las solicitudes";
	public static String ERROR_RECUPERAR_CONTACTOS = "Error al intentar traer los contactos";
	public static String ERROR_RECUPERAR_USUARIOS = "Error al intentar traer los usuarios";
	public static String ERROR_RESPONDER_SOLICITUD = "Error al intentar aceptar o rechazar la solicitud";
	public static String ERROR_YA_EXISTE_USUARIO = "Ya existe una cuenta con ese mail, ingrese otro";
	public static String ERROR_LOGIN = "Error al intentar ingrear a la aplicacion";
	public static String NO_EXISTE_USUARIO = "El email o password es incorrecto";
	public static String NO_EXISTE_EMAIL_USUARIO = "El email no se encuentra en el sistema";
	public static String ERROR_ACTUALIZAR_UBICACION = "Error al intentar actualizar su ubicacion";

	// Mensajes de error sitios
	public static String ERROR_CREAR_SITIO = "Error al intentar crear el sitio";
	public static String ERROR_ELIMINAR_SITIO = "Error al intentar eliminar el sitio";
	public static String ERROR_MODIFICAR_SITIO = "Error al intentar modificar el sitio";
	public static String ERROR_BUSCAR_SITIO = "Error al buscar el sitio";
	public static String ERROR_CARGAR_CATEGORIAS = "Error al cargar las categorias";
	public static String ERROR_LISTA_SITIO = "Error al obtener los sitios, intentelo luego";
	public static String ERROR_USUARIO_NO_AUTORIZADO = "No se puede modificar o borrar un sitio que lo fue creado por usted";

	// Mensajes de error publicacion
	public static String ERROR_CREAR_PUBLICACION = "Error al intentar crear la publicacion";

	// Mensajes para el envio de mail
	public static String EMAIL_SUBJECT_SOLICITUD_AMISTAD = " te quiere agregar como amigo";
	public static String EMAIL_SUBJECT_INVITACION = " quiere que pertenezcas a la red GSIAM";

}
