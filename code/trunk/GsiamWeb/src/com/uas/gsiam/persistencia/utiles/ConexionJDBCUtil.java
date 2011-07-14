/**
 * 
 */
package com.uas.gsiam.persistencia.utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.taglibs.standard.lang.jstl.Coercions;



public class ConexionJDBCUtil {


	static String driver = "org.postgresql.Driver";
	static String connectString = "jdbc:postgresql://localhost:5432/BD_GSIAM";
	static String user = "postgres";
	static String password = "postgres";
	
	private static Connection conexion = null;
	
	static {
		try {
			
			Class.forName(driver);
			conexion = DriverManager.getConnection(connectString, user, password);
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	
	public static Connection getConexion()  {
		return conexion;
	}
	
	public void cerrarConexion() throws SQLException{
		if(conexion != null){
			conexion.close();
		}
	}

	
	
}