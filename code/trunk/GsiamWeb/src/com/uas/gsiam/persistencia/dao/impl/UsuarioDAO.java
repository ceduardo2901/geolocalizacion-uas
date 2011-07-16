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
		
		PreparedStatement ps;
		String nombre = null;
		
		try {
			
			String sqlLogin = "SELECT u.usu_nombre FROM t_usuario u " +
	                          "WHERE u.usu_mail = ? AND u.usu_password = ?";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlLogin);

			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getPassword());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				nombre = rs.getString("usu_nombre");	
			}
				
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return nombre;		
		
	}
	
	
	public boolean existeUsuario(UsuarioDTO usuario){
		
		PreparedStatement ps;
		boolean existe = false;
		
		try {
			
			
			String sqlExisteUsuario = "SELECT COUNT(*) FROM t_usuario u " +
            						  "WHERE u.usu_mail = ?";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlExisteUsuario);
			
			ps.setString(1, usuario.getEmail());
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			if (rs.getInt(1) == 1)
				 existe = true;
			 
			 rs.close();
			 ps.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return existe;		
		
	}
	
	
	// TODO Ver si retornamos el id para seguir teniendo referencia.. o agregar el campo id al DTO y retornar el DTO
	// TODO Definir donde se encripta el password. 
	/*
	 * Metodo que crea al usuario
	 */
	public int crearUsuario(UsuarioDTO usuario){
		
		PreparedStatement ps;
		int idUsuario = 0;
		
		try {
			
			String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password) " +
					                 "VALUES (?, ?, ?) RETURNING usu_id";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
			
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getPassword());
				
			ResultSet rsid = ps.executeQuery();
			rsid.next();
			idUsuario = rsid.getInt(1);
			
			rsid.close();
			ps.close();
 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return idUsuario;		
		
	}
	
	
	
	
	
	
}
