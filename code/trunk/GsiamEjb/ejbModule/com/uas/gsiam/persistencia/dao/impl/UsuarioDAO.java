package com.uas.gsiam.persistencia.dao.impl;


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;



public class UsuarioDAO implements IUsuarioDAO {
	
	
	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion{
		
		PreparedStatement ps;
		UsuarioDTO usuarioRetorno = new UsuarioDTO();
				
		try {
			
			String sqlLogin = "SELECT * FROM t_usuario u " +
	                          "WHERE u.usu_mail = ? AND u.usu_password = ?";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlLogin);

			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getPassword());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setFechaNacimiento(rs.getDate("usu_fecha_nacimiento"));
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setEmail(usuario.getEmail());
				usuarioRetorno.setPassword(usuario.getPassword());
				
			}
				
			rs.close();
			ps.close();
			
			
		} catch (SQLException e) {
			throw new UsuarioNoExisteExcepcion(e.getMessage());
		}
		
		return usuarioRetorno;		
		
	}
	
	
	public boolean existeUsuario(String mail) throws SQLException{
		
		PreparedStatement ps;
		boolean existe = false;
			
		String sqlExisteUsuario = "SELECT COUNT(*) FROM t_usuario u " +
        						  "WHERE u.usu_mail = ?";
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlExisteUsuario);
		
		ps.setString(1, mail);
		
		ResultSet rs = ps.executeQuery();
		
		rs.next();
		if (rs.getInt(1) == 1)
			 existe = true;
		 
		 rs.close();
		 ps.close();
			
	
		
		return existe;		
		
	}
	
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que crea al usuario
	 */
	public void crearUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
	
		
		String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password) " +
				                 "VALUES (?, ?, ?)";
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
		
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getPassword());
	//	ps.setDate(4, usuario.getFechaNacimiento());
		
		ps.executeUpdate();
		ps.close();
		
	}
	
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que modifica al usuario
	 */
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
	
		String sqlModificarUsuario = "UPDATE t_usuario SET usu_nombre = ?, usu_mail = ?, usu_password = ?, usu_fecha_nacimiento = ? " +
				                 "WHERE usu_id = ?";
		

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlModificarUsuario);
		
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getPassword());
		//ps.setDate(4, usuario.getFechaNacimiento());
		
		ps.setInt(5, usuario.getId());
			
		ps.close();
 
			
		
		
	}
	
	
	/*
	 * Metodo que elimina usuario
	 */
	public void eliminarUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
					
		String sqlModificarUsuario = "DELETE t_usuario WHERE usu_id = ?";
		// TODO Ver como se eliminar los contactos del mismo

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlModificarUsuario);
		
		ps.setInt(1, usuario.getId());
		
		ps.close();
 
		
	}
	
	//TODO Deberiamos pasar solo el id del usuario?? o esta bien pasar todo el objeto??
	//TODO Deberiamos pasar el objeto SolicitudContacto??
	
	public void crearContacto(int usuarioSolicitante, int idUsuarioAprobador) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlCrearContacto = "INSERT INTO t_contacto (con_id_usuario_sol, con_id_usuario_apr, con_fecha_solicitud, con_fecha_aprobacion) " +
				                  "VALUES (?, ?, ?, ?)";
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearContacto);
		
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());

		
		ps.setInt(1, usuarioSolicitante);
		ps.setInt(2, idUsuarioAprobador);
		ps.setDate(3, sqlToday);
		ps.setDate(4, null);
		
		ps.executeUpdate();
					
		ps.close();
 
			
	
		
	}
	

	public void modificarFechaAprobacionContacto(UsuarioDTO usuarioSolicitante, UsuarioDTO usuarioAprobador, Date FechaAprovacion) throws SQLException{
		
		PreparedStatement ps;

		String sqlAprobarContacto = "UPDATE t_contacto SET con_fecha_aprobacion = ? " +
				                    "WHERE con_id_usuario_sol = ? AND con_id_usuario_apr = ?";
	
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlAprobarContacto);
				
		ps.setDate(1, FechaAprovacion);
		ps.setInt(2, usuarioSolicitante.getId());
		ps.setInt(3, usuarioAprobador.getId());
		
		ps.executeUpdate();
					
		ps.close();
 
		
	}
	
	
	// TODO : Ver como resolver cuando uno se elimina una amistad ya estando aprobada....
	public void eliminarContacto(UsuarioDTO usuarioSolicitante, UsuarioDTO usuarioAprobador) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlEliminarContacto = "DELETE t_contacto WHERE con_id_usuario_sol=? AND con_id_usuario_apr = ?";

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlEliminarContacto);
			
		ps.setInt(1, usuarioSolicitante.getId());
		ps.setInt(1, usuarioAprobador.getId());
		
		ps.close();
 
		
		
	}
	
}
