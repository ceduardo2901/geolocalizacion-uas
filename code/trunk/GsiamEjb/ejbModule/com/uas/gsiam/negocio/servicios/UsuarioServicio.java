package com.uas.gsiam.negocio.servicios;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;

@Remote
public interface UsuarioServicio {

	static final String SERVICE_ADDRESS = "java:global/Gsiam/GsiamEjb/UsuarioServicio";
	
	public UsuarioDTO login (UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;

	public void crearUsuario (UsuarioDTO usuario) throws UsuarioExcepcion;
		
	public void modificarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion;
		
	public void eliminarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion;	
		
	public void crearSolicitudContacto (SolicitudContactoDTO solicitud) throws UsuarioExcepcion;
	
	public void responderSolicitudContacto (SolicitudContactoDTO solicitud, boolean respuesta) throws UsuarioExcepcion;
	
	public ArrayList<UsuarioDTO> getSolicitudesContactosPendientes (UsuarioDTO usuario) throws UsuarioExcepcion;
	
	public ArrayList<UsuarioDTO> getContactos (int idUsuario) throws UsuarioExcepcion;
	
	public ArrayList<UsuarioDTO> getUsuarios (String nombre) throws UsuarioExcepcion;
		
}
