package com.uas.gsiam.negocio.servicios;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;

public interface IUsuarioServicio {

	
	public UsuarioDTO login (UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;

	public void crearUsuario (UsuarioDTO usuario);
	
}
