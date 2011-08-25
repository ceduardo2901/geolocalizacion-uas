package com.uas.gsiam.persistencia.dao;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

import com.uas.gsiam.negocio.dto.SolicitudContacto;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;



public interface IUsuarioDAO {

	
	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;
	
	public boolean existeUsuario(String mail) throws SQLException;
	
	public void crearUsuario(UsuarioDTO usuario) throws SQLException;
		
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException;
	
	public void eliminarUsuario(UsuarioDTO usuario) throws SQLException;
	
	public void crearContacto(SolicitudContacto solicitud) throws SQLException;
	
	public void modificarFechaAprobacionContacto(UsuarioDTO usuarioSolicitante, UsuarioDTO usuarioAprobador, Date FechaAprovacion) throws SQLException;
	
	public void eliminarContacto(UsuarioDTO usuarioSolicitante, UsuarioDTO usuarioAprobador) throws SQLException;
	
	public ArrayList<UsuarioDTO> getSolicitudesContactosPendientes(UsuarioDTO usuario) throws SQLException;
	
}
