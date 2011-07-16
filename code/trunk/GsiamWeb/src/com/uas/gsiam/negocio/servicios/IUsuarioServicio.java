package com.uas.gsiam.negocio.servicios;

import com.uas.gsiam.negocio.dto.UsuarioDTO;

public interface IUsuarioServicio {

	
	public void login (UsuarioDTO usuario);

	public void crearUsuario (UsuarioDTO usuario);
	
}
