package com.uas.gsiam.persistencia.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;



public class UsuarioDAO implements IUsuarioDAO {
	
	//TODO Ver como manejamos las conexiones y el abstract factory.
	
	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion{
		
		PreparedStatement ps;
		UsuarioDTO usuarioRetorno = null;
				
		try {
			
			String sqlLogin = "SELECT * FROM t_usuario u " +
	                          "WHERE u.usu_mail = ? AND u.usu_password = ?";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlLogin);

			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getPassword());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				usuario.setNombre(rs.getString("usu_nombre"));	
				usuario.setFechaNacimiento(rs.getDate("usu_fecha_nacimiento"));
				usuario.setId(rs.getInt("usu_id"));
				usuario.setEmail(usuario.getEmail());
				usuario.setPassword(usuario.getPassword());
				
			}
				
			rs.close();
			ps.close();
			
			
		} catch (SQLException e) {
			throw new UsuarioNoExisteExcepcion(e.getMessage());
		}
		
		return usuarioRetorno;		
		
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
	
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que crea al usuario
	 */
	public void crearUsuario(UsuarioDTO usuario){
		
		PreparedStatement ps;
	
		try {
			
			String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password) " +
					                 "VALUES (?, ?, ?)";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
			
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getPassword());
		//	ps.setDate(4, usuario.getFechaNacimiento());
			
			ps.executeUpdate();
			/*	
			ResultSet rsid = ps.executeQuery();
			rsid.next();
			idUsuario = rsid.getInt(1);
			rsid.close();
			*/
			
			ps.close();
 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que modifica al usuario
	 */
	public void modificarUsuario(UsuarioDTO usuario){
		
		PreparedStatement ps;
		
		try {
			
			String sqlModificarUsuario = "UPDATE t_usuario SET usu_nombre = ?, usu_mail = ?, usu_password = ?, usu_fecha_nacimiento = ? " +
					                 "WHERE usu_id = ?";
			

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlModificarUsuario);
			
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getPassword());
			//ps.setDate(4, usuario.getFechaNacimiento());
			ps.setInt(5, usuario.getId());
			
			ps.close();
 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que elimina usuario
	 */
	public void eliminarUsuario(UsuarioDTO usuario){
		
		PreparedStatement ps;
		
		try {
			
			String sqlModificarUsuario = "DELETE t_usuario WHERE usu_id = ?";
			// TODO Ver como se eliminar los contactos del mismo

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlModificarUsuario);
			
			ps.setInt(1, usuario.getId());
			
			ps.close();
 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
	}
	
}
