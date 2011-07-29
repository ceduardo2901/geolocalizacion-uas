package com.uas.gsiam.persistencia.postgresql;

import com.uas.gsiam.persistencia.AbstractFactory;
import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.dao.impl.UsuarioDAO;


/**
 *	Clase que que hereda de AbstractFactory y crea los DAOs para postgresql  
 */

public class PostgreSqlFactory extends AbstractFactory{
	

	public IUsuarioDAO getUsuarioDAO() {
		return new UsuarioDAO();
	}
	
	public ISitioDAO getSitioDAO(){
		return null;
	}
	
}
