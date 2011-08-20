package com.uas.gsiam.persistencia.dao;

import java.sql.Date;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;



public interface IUsuarioDAO {

	
	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;
	
	public boolean existeUsuario(UsuarioDTO usuario);
	
	public void crearUsuario(UsuarioDTO usuario);
		
	public void modificarUsuario(UsuarioDTO usuario);
	
	public void eliminarUsuario(UsuarioDTO usuario);
	
	public void crearContacto(int usuarioSolicitante, int idUsuarioAprobador);
	
	public void modificarFechaAprobacionContacto(UsuarioDTO usuarioSolicitante, UsuarioDTO usuarioAprobador, Date FechaAprovacion);
	
	public void eliminarContacto(UsuarioDTO usuarioSolicitante, UsuarioDTO usuarioAprobador);
	
	
}
