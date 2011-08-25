package com.uas.gsiam.persistencia.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.uas.gsiam.negocio.dto.SolicitudContacto;
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
		
		try{

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
		
		}finally{
			
				if (ConexionJDBCUtil.getConexion() != null)
					ConexionJDBCUtil.getConexion().close();
	
		}
		
		return existe;		
		
	}
	
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que crea al usuario
	 */
	public void crearUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
		
		try{
		
			String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password) " +
					                 "VALUES (?, ?, ?)";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
			
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getPassword());
			
			ps.executeUpdate();
			ps.close();
			
		}finally{
			
				if (ConexionJDBCUtil.getConexion() != null)
					ConexionJDBCUtil.getConexion().close();

		}
		
		
	}
	
	
	// TODO Definir donde se encripta el password. 
	// TODO Falta poner el tipo de la foto del usuario...
	/*
	 * Metodo que modifica al usuario
	 */
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
	
		String sqlModificarUsuario = "UPDATE t_usuario SET usu_nombre = ?, usu_mail = ?, usu_password = ? " +
				                 "WHERE usu_id = ?";
		

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlModificarUsuario);
		
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getPassword());
		ps.setInt(4, usuario.getId());
		ps.executeUpdate();
		
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
	
	public void crearContacto(SolicitudContacto solicitud) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlCrearContacto = "INSERT INTO t_contacto (con_id_usuario_sol, con_id_usuario_apr, con_fecha_solicitud, con_fecha_aprobacion) " +
				                  "VALUES (?, ?, ?, ?)";
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearContacto);
		
		java.util.Date today = new java.util.Date();
		java.sql.Date sqlToday = new java.sql.Date(today.getTime());

		
		ps.setInt(1, solicitud.getIdUsuarioSolicitante());
		ps.setInt(2, solicitud.getIdUsuarioAprobador());
		ps.setDate(3, sqlToday);
		ps.setDate(4, null);
		
		ps.executeUpdate();
					
		ps.close();
 
			
	
		
	}
	

	public void aprobarSolicitudContacto(SolicitudContacto solicitud) throws SQLException{
		
		PreparedStatement ps;

		String sqlAprobarContacto = "UPDATE t_contacto SET con_fecha_aprobacion = ? " +
				                    "WHERE con_id_usuario_sol = ? AND con_id_usuario_apr = ?";
	
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlAprobarContacto);
				
		ps.setDate(1, solicitud.getFechaAprobacion());
		ps.setInt(2, solicitud.getIdUsuarioSolicitante());
		ps.setInt(3, solicitud.getIdUsuarioAprobador());
		
		ps.executeUpdate();
					
		ps.close();
 
		
	}
	
	
	// TODO : Ver como resolver cuando uno se elimina una amistad ya estando aprobada.... en caso que lo implementemos
	public void eliminarSolicitudContacto(SolicitudContacto solicitud) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlEliminarContacto = "DELETE t_contacto WHERE con_id_usuario_sol=? AND con_id_usuario_apr = ?";

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlEliminarContacto);
			
		ps.setInt(1, solicitud.getIdUsuarioSolicitante());
		ps.setInt(1, solicitud.getIdUsuarioAprobador());
		
		ps.close();

	}
	
	
	
	
	public ArrayList<UsuarioDTO> getSolicitudesContactosPendientes(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
		ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
				
		try{
			
			String sqlSolicitudesUsuarios = "SELECT u.* FROM t_contacto c, t_usuario u " +
											"WHERE c.con_id_usuario_apr = ? AND " +
											"u.usu_id = c.con_id_usuario_sol AND " +
											"c.con_fecha_aprobacion is null";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlSolicitudesUsuarios);
	
			ps.setInt(1, usuario.getId());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				UsuarioDTO usuarioRetorno = new UsuarioDTO();
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setEmail(rs.getString("usu_mail"));
				usuarioRetorno.setPassword(rs.getString("usu_password"));
				
				listaUsuarios.add(usuarioRetorno);
				
				
			}
				
			rs.close();
			ps.close();
				
			
			return listaUsuarios;	
		
		
		}finally{
			
			if (ConexionJDBCUtil.getConexion() != null)
				ConexionJDBCUtil.getConexion().close();

	}
			
		
}
	
	
	
	
	
	
}
