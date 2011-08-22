package com.uas.gsiam.negocio.servicios;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.SolicitudContacto;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;

@Remote
public interface UsuarioServicio {

	static final String SERVICE_ADDRESS = "java:global/Gsiam/GsiamEjb/UsuarioServicio";
	
	public UsuarioDTO login (UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;

	public String crearUsuario (UsuarioDTO usuario) throws UsuarioExcepcion;
		
	public String modificarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion;
		
	public String eliminarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion;	
		
	public String crearSolicitudContacto (SolicitudContacto solicitud) throws UsuarioExcepcion;	
		
}
