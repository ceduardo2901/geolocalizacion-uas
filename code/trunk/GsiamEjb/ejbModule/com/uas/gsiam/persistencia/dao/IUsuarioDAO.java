package com.uas.gsiam.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;



public interface IUsuarioDAO {

	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;
	
	public boolean existeUsuario(String mail) throws SQLException;
	
	public UsuarioDTO getUsuario(int id) throws SQLException;
	
	public ArrayList<UsuarioDTO> getUsuarios(String nombre) throws SQLException;
	
	public void crearUsuario(UsuarioDTO usuario) throws SQLException;
		
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException;
	
	public void eliminarUsuario(UsuarioDTO usuario) throws SQLException;
	
	public void crearContacto(SolicitudContactoDTO solicitud) throws SQLException;
	
	public void aprobarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException;
	
	public void eliminarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException;
	
	public ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(UsuarioDTO usuario) throws SQLException;
	
	public ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(UsuarioDTO usuario) throws SQLException;
	
	public ArrayList<UsuarioDTO> getContactos(int idUsuario) throws SQLException;
	
	
	
}
