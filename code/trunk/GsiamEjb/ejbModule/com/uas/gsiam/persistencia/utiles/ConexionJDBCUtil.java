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
			
			
			
			System.out.println("ACA ESTOY");
			Context ctx = new InitialContext();
			dt = (DataSource) ctx.lookup("java:PostgreSqlDS");
			System.out.println("ACA ESTOY TERMIONE");
			
		} catch (Exception e) {
			e.printStackTrace();
		
		}
	}

	
	public static Connection getConexion() throws SQLException  {
		return dt.getConnection();
	}
	
	

	
}