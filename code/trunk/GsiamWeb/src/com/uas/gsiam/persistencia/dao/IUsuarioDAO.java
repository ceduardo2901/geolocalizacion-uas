package com.uas.gsiam.persistencia.dao;

import com.uas.gsiam.negocio.dto.UsuarioDTO;



public interface IUsuarioDAO {

	
	public UsuarioDTO login(UsuarioDTO usuario);
	
	public boolean existeUsuario(UsuarioDTO usuario);
	
	public void crearUsuario(UsuarioDTO usuario);
		
	public void modificarUsuario(UsuarioDTO usuario);
	
	public void eliminarUsuario(UsuarioDTO usuario);
}
