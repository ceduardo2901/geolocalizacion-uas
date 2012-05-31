package com.uas.gsiam.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;

public interface IUsuarioDAO {

	/**
	 * 
	 * @param usuario
	 * @return
	 * @throws UsuarioNoExisteExcepcion
	 */
	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;

	/**
	 * Determina si el usuario existe en el sistema a partir del email de este y al flag activo o no activo
	 * 
	 * @param mail, activo
	 *            Email del usuario
	 * @return Retorna true si existe false en caso contrario
	 * @throws SQLException
	 */
	public boolean existeUsuario(String mail, int activo) throws SQLException;

	/**
	 * Retorna el usuario con el identificador ingresado por parametro
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna el usuario correspondiente
	 * @throws SQLException
	 */
	public UsuarioDTO getUsuario(int id) throws SQLException;

	/**
	 * Retorna todos los usuarios cuyo nombre contiene el nombre ingresado por
	 * parametro que no sean amigos del id ingresado.
	 * 
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<UsuarioDTO> getUsuarios(int id, String nombre)
			throws SQLException;

	/**
	 * Crea un usuario en el sistema
	 * 
	 * @param usuario
	 *            Usuario a crear en el sistema
	 * @throws SQLException
	 */
	public void crearUsuario(UsuarioDTO usuario) throws SQLException;

	/**
	 * Modifica un usuario del sistema con los nuevos datos ingresados
	 * 
	 * @param usuario
	 *            Datos del usuario a modificar
	 * @throws SQLException
	 */
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException;

	/**
	 * Desactiva la cuenta de usuario del sistema. Setea en cero el atributo
	 * activado
	 * 
	 * @param usuario
	 *            Usuario a eliminar logicamente del sistema
	 * @throws SQLException
	 */
	public void desactivarUsuario(UsuarioDTO usuario) throws SQLException;

	/**
	 * 
	 * Elimina los contactos del usuario ingresado por parametros
	 * 
	 * @param usuario
	 *            Usuario al cual se le eliminaran los contactos
	 * @throws SQLException
	 */
	public void eliminarContactos(UsuarioDTO usuario) throws SQLException;

	/**
	 * Crea un contacto a partir de la solicitud de amistad ingresada
	 * 
	 * @param solicitud
	 *            Solicitud de contacto
	 * @throws SQLException
	 */
	public void crearContacto(SolicitudContactoDTO solicitud)
			throws SQLException;

	/**
	 * Aprueba la solicitud de amistad ingresada
	 * 
	 * @param solicitud
	 *            Solicitud ingresada
	 * @throws SQLException
	 */
	public void aprobarSolicitudContacto(SolicitudContactoDTO solicitud)
			throws SQLException;

	/**
	 * Elimina una solicitud de amistad ingresada anteriormente
	 * 
	 * @param solicitud
	 *            Solicitud de amistad a eliminar
	 * @throws SQLException
	 */
	public void eliminarSolicitudContacto(SolicitudContactoDTO solicitud)
			throws SQLException;

	/**
	 * Obtener todos los usuario con solicitudes recibidas pendientes de
	 * procesar con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 * @throws SQLException
	 */
	public ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(int idUsuario)
			throws SQLException;

	/**
	 * Obtener todos los usuario con solicitudes enviadas pendientes de procesar
	 * con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 * @throws SQLException
	 */
	public ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(int idUsuario)
			throws SQLException;

	/**
	 * Obtener todos los amigos del usuario correspondiente
	 * 
	 * @param idUsuario
	 *            Identificador de usuario
	 * @return Retorna la lista de usuarios que son amigos del usuario ingresado
	 * @throws SQLException
	 */
	public ArrayList<UsuarioDTO> getContactos(int idUsuario)
			throws SQLException;

	/**
	 * Actualiza la posición geografica del usuario
	 * 
	 * @param posUsuario
	 *            Posición geografica del usuario
	 * @throws SQLException
	 */
	public void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario)
			throws SQLException;

}
