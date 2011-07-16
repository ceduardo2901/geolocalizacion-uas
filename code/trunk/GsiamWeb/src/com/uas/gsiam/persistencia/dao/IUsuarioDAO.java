package com.uas.gsiam.persistencia.dao;

import com.uas.gsiam.negocio.dto.UsuarioDTO;



public interface IUsuarioDAO {

	
	public String login(UsuarioDTO usuario);
	
	public int crearUsuario(UsuarioDTO usuario);
		
}
