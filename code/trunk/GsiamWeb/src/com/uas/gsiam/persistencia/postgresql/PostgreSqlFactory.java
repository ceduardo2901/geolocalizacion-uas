package com.uas.gsiam.persistencia.postgresql;

import com.uas.gsiam.persistencia.AbstractFactory;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.postgresql.dao.UsuarioDAOPostgreSql;


/**
 *	Clase que que hereda de AbstractFactory y crea los DAOs para mysql  
 */

public class PostgreSqlFactory extends AbstractFactory{
	
	
	/**
	 * Crea un nuevo ClienteDAOMySql
	 */
	@Override
	public IUsuarioDAO getUsuarioDAO() {
		return new UsuarioDAOPostgreSql();
	}
	
	
}
