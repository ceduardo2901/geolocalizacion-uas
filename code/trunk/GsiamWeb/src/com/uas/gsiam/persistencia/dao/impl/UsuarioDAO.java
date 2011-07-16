package com.uas.gsiam.persistencia.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;



public class UsuarioDAO implements IUsuarioDAO {
	
	//TODO Ver como manejamos las conexiones y el abstract factory.
	
	public String login(UsuarioDTO usuario){
		
		PreparedStatement stmt;
		String nombre = null;
		
		try {
			
			String sqlLogin = "SELECT t_usuario.usu_nombre FROM public.t_usuario " +
	                          "WHERE t_usuario.usu_mail = ? AND t_usuario.usu_password = ?";
			
			stmt = ConexionJDBCUtil.getConexion().prepareStatement(sqlLogin);
			stmt.setString(1, usuario.getEmail());
			stmt.setString(2, usuario.getPassword());
			
			ResultSet rs = stmt.executeQuery(sqlLogin);

			 while ( rs.next() ) {
					nombre = rs.getString( "usu_nombre" );
					System.out.println( nombre );
				    }
			
			rs.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nombre;		
		
	}
	
	// TODO Ver si retornamos el id para seguir teniendo referencia.. o agregar el campo id al DTO y retornar el DTO
	// TODO Definir donde se encripta el password. 
	// TODO Ver como retornamos el id
	public int crearUsuario(UsuarioDTO usuario){
		
		PreparedStatement stmt;
		int idUsuario = 0;
		
		try {
			
			String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password) " +
					                 "VALUES (?, ?, ?) RETURNING usu_id";
			
			
			stmt = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
			stmt.setString(1, usuario.getNombre());
			stmt.setString(2, usuario.getEmail());
			stmt.setString(3, usuario.getPassword());
			
		//	stmt.executeUpdate(sqlCrearUsuario);
			
			ResultSet rsgfid = stmt.executeQuery();
			rsgfid.next();
			idUsuario = rsgfid.getInt(1);
			rsgfid.close();
			stmt.close();
 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idUsuario;		
		
	}
	
	
	
	
	
	
}
