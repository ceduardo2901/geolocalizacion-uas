package com.uas.gsiam.negocio.servicios;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.utiles.Constantes;

@Remote
public interface UsuarioServicio {

	static final String SERVICE_ADDRESS = "java:global/Gsiam/GsiamEjb/UsuarioServicio";

	/**
	 * Metodo para loguearse al sistema a partir de un email y contraseña
	 * 
	 * @param usuario
	 *            Email y contraseña
	 * @return Si el email y contraseña existen en el sistema retorna el usuario
	 *         autenticado si no se tira una excepción
	 * @throws UsuarioNoExisteExcepcion
	 * @throws UsuarioExcepcion
	 */
	public UsuarioDTO login(UsuarioDTO usuario)
			throws UsuarioNoExisteExcepcion, UsuarioExcepcion;

	/**
	 * Crea un nuevo usuario en el sistema
	 * 
	 * @param usuario
	 *            Datos para crear el nuevo usuario
	 * @throws UsuarioExcepcion
	 */
	public void crearUsuario(UsuarioDTO usuario) throws UsuarioExcepcion;

	/**
	 * Modifica un usuario del sistema
	 * 
	 * @param usuario
	 *            Datos a modificar
	 * @throws UsuarioExcepcion
	 */
	public void modificarUsuario(UsuarioDTO usuario) throws UsuarioExcepcion;

	/**
	 * Cierra
	 * 
	 * @param usuario
	 * @throws UsuarioExcepcion
	 */
	public void cerrarCuenta(UsuarioDTO usuario) throws UsuarioExcepcion;

	/**
	 * Crea una solicitud de Amistad en el sistema
	 * 
	 * @param solicitud
	 *            Solicitud a crear
	 * @throws UsuarioExcepcion
	 */
	public void crearSolicitudContacto(SolicitudContactoDTO solicitud)
			throws UsuarioExcepcion;

	/**
	 * Metodo para aceptar una solicitud de amistad
	 * 
	 * @param solicitud
	 *            Solicitud ha aceptar
	 * @param accion
	 *            Acción de aceptar solicitud {@link Constantes}
	 * @throws UsuarioExcepcion
	 */
	public void responderSolicitudContacto(SolicitudContactoDTO solicitud,
			int accion) throws UsuarioExcepcion;

	/**
	 * Obtener todos los usuario con solicitudes enviadas pendientes de procesar
	 * con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 * @throws UsuarioExcepcion
	 */
	public ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(int id)
			throws UsuarioExcepcion;

	/**
	 * Obtener todos los usuario con solicitudes recibidas pendientes de
	 * procesar con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 * @throws UsuarioExcepcion
	 */
	public ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(int id)
			throws UsuarioExcepcion;

	/**
	 * Servicio que devuelve todos los amigos de un usuario del sistema
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @return Lista de amigos del usuario ingresado
	 * @throws UsuarioExcepcion
	 */
	public ArrayList<UsuarioDTO> getAmigos(int idUsuario)
			throws UsuarioExcepcion;

	/**
	 * Retorna todos los usuarios cuyo nombre contiene el nombre ingresado por
	 * parametro que no sean amigos del id ingresado.
	 * 
	 * @param id
	 * @param nombre
	 * @throws UsuarioExcepcion
	 */
	public ArrayList<UsuarioDTO> getUsuarios(int id, String nombre)
			throws UsuarioExcepcion;

	/**
	 * Metodo para enviar una invitacion a una persona que no se encuentra en el
	 * sistema
	 * 
	 * @param direccion
	 *            Direccion de emial de la persona a invitar al sistema
	 * @param nombre
	 *            Nombre del usuario del sistema
	 * @throws UsuarioExcepcion
	 */
	public void enviarInvitaciones(String direccion, String nombre)
			throws UsuarioExcepcion;

	/**
	 * Servicio que actualiza la posición geografica del usuario
	 * 
	 * @param posUsuario
	 *            Posición geografica
	 * @throws UsuarioExcepcion
	 */
	public void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario)
			throws UsuarioExcepcion;

}
