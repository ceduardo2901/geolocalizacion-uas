/**
 * 
 */
package com.uas.gsiam.persistencia.utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class ConexionJDBCUtil {


	//TODO tener una lcase Constantes???
	private static final String archivo = "com.uas.obligatorio.negocio.utiles.configuracion";
	private static ResourceBundle rb = ResourceBundle.getBundle(archivo);
	
	
	private static String driver = rb.getString("driver");
	private static String connectString = rb.getString("url");
	private static String user = rb.getString("user");
	private static String password = rb.getString("pass");
	
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