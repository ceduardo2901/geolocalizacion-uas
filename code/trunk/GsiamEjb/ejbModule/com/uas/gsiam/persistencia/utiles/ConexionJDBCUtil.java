/**
 * 
 */
package com.uas.gsiam.persistencia.utiles;

import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;


public class ConexionJDBCUtil {
	
	
	private static DataSource dt;
	
	
	static {
		try {
			
			
			Context ctx = new InitialContext();
			dt = (DataSource) ctx.lookup("java:DS_Postgresql");
			

		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	
	public static Connection getConexion() throws SQLException  {
		return dt.getConnection();
	}
	
	

	
}