package com.uas.gsiam.negocio.servicios;
import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;

@Remote
public interface UsuarioServicio {

	public UsuarioDTO login (UsuarioDTO usuario) throws UsuarioNoExisteExcepcion;

	public void crearUsuario (UsuarioDTO usuario);
	
}
