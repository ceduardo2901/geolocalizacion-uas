package com.uas.gsiam.negocio.sl;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ServiceLocatorNegocio {

	private static ServiceLocatorNegocio instancia;
	private DataSource dt;
	
	//TODO Hay que poner el datasource nuevo
	private ServiceLocatorNegocio() throws NamingException {
		Context ctx = new InitialContext();
		dt = (DataSource) ctx.lookup("java:MySqlDS");
		
	}

	public static ServiceLocatorNegocio getInstancia() throws NamingException {
		if (instancia == null)
			instancia = new ServiceLocatorNegocio();
		return instancia;
	}

	public Connection getConnection() throws SQLException{
		return dt.getConnection();
	}
}
