package com.uas.gsiam.persistencia.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;



public class UsuarioDAO implements IUsuarioDAO {
	
	public String login(UsuarioDTO usuario){
		
		PreparedStatement stmt;
		String nombre = null;
		
		try {
			
			String sqlLogin = "SELECT t_usuario.usu_nombre FROM public.t_usuario " +
	                          "WHERE t_usuario.usu_mail = ? AND t_usuario.usu_password = ?";
			
			stmt = ConexionJDBCUtil.getConexion().prepareStatement(sqlLogin);
			stmt.setString(0, usuario.getEmail());
			stmt.setString(1, usuario.getPassword());
			
			ResultSet rs = stmt.executeQuery(sqlLogin);

			 while ( rs.next() ) {
					nombre = rs.getString( "usu_nombre" );
					System.out.println( nombre );
				    }
			
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nombre;
		

		
				
		
	}
	
}
