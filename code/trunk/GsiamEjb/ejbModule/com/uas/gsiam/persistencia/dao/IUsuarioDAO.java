package com.uas.gsiam.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;



public interface IUsuarioDAO {

	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;
	
	public boolean existeUsuario(String mail) throws SQLException;
	
	public UsuarioDTO getUsuario(int id) throws SQLException;
	
	/**
	 * Retorna todos los usuarios cuyo nombre contiene el nombre ingresado por parametro que no sean amigos del id ingresado.
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<UsuarioDTO> getUsuarios(int id, String nombre) throws SQLException;
	
	public void crearUsuario(UsuarioDTO usuario) throws SQLException;
		
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException;
	
	public void desactivarUsuario(UsuarioDTO usuario) throws SQLException;
	
	public void eliminarContactos(UsuarioDTO usuario) throws SQLException;
	
	public void crearContacto(SolicitudContactoDTO solicitud) throws SQLException;
	
	public void aprobarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException;
	
	public void eliminarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException;
	
	public ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(int idUsuario) throws SQLException;
	
	public ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(int idUsuario) throws SQLException;
	
	public ArrayList<UsuarioDTO> getContactos(int idUsuario) throws SQLException;
	
	public void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario) throws SQLException;
	
}
