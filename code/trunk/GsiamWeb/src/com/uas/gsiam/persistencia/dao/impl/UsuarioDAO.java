package com.uas.gsiam.persistencia.dao.impl;

import java.sql.Connection;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;



public class UsuarioDAO implements IUsuarioDAO {

	private Connection conn;
	
	public void login(UsuarioDTO usuario){
		conn = ConexionJDBCUtil.getConexion();
	}
	
}
