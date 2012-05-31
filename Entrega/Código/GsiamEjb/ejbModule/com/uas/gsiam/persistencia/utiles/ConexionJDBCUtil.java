/**
 * 
 */
package com.uas.gsiam.persistencia.utiles;

import java.sql.Connection;
import java.sql.SQLException;

import javax.inject.Inject;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * Clase encargada de manejar la conexiones con la base de datos a partir de un
 * datasource
 * 
 * @author Antonio
 * 
 */
public class ConexionJDBCUtil {

	private static DataSource dt;

	static {
		try {

			Context ctx = new InitialContext();
			dt = (DataSource) ctx.lookup("java:GsiamDS");

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public static Connection getConexion() throws SQLException {
		return dt.getConnection();
	}

	public static void cerrarConexion() throws SQLException {
		if (dt != null) {
			dt.getConnection().close();
		}
	}

}