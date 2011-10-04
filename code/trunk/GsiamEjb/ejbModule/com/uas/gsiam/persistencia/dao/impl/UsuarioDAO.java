package com.uas.gsiam.persistencia.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
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
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
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
	
	

	public UsuarioDTO getUsuario(int id) throws SQLException{
		
		PreparedStatement ps;
		UsuarioDTO usuarioRetorno = null;
		
		try{

			String sqlGetUsuario = "SELECT * FROM t_usuario u " +
	        						  "WHERE u.usu_id = ?";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlGetUsuario);
			
			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				usuarioRetorno = new UsuarioDTO();
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setEmail(rs.getString("usu_mail"));
				usuarioRetorno.setPassword(rs.getString("usu_password"));
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
			}
				
			rs.close();
			ps.close();
			
		
		}finally{
			
				if (ConexionJDBCUtil.getConexion() != null)
					ConexionJDBCUtil.getConexion().close();
	
		}
		
		return usuarioRetorno;		
		
	}
	
	/**
	 * Retorna todos los usuarios cuyo nombre contiene el nombre ingresado por parametro
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<UsuarioDTO> getUsuarios(String nombre) throws SQLException{
		
		PreparedStatement ps;
		ArrayList<UsuarioDTO> listaRetorno = new ArrayList<UsuarioDTO>();
		
		try{

			String sqlGetUsuarios = "SELECT * FROM t_usuario u " +
	        						"WHERE UPPER(u.usu_nombre) like ? " +
	        						"ORDER BY u.usu_nombre";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlGetUsuarios);
			

			ps.setString(1, "%" + nombre.toUpperCase() +"%");
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setId(rs.getInt("usu_id"));
				usuario.setNombre(rs.getString("usu_nombre"));	
				usuario.setEmail(rs.getString("usu_mail"));
				usuario.setPassword(rs.getString("usu_password"));
				usuario.setAvatar(rs.getBytes("usu_avatar"));
				
				listaRetorno.add(usuario);
			}
				
			rs.close();
			ps.close();
			
		
		}finally{
			
				if (ConexionJDBCUtil.getConexion() != null)
					ConexionJDBCUtil.getConexion().close();
	
		}
		
		return listaRetorno;		
		
	}
	
	
	/*
	 * Metodo que crea al usuario
	 */
	public void crearUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
		
		try{
		
			String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password, usu_avatar) " +
					                 "VALUES (?, ?, ?, ?)";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
			
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getPassword());
			ps.setBytes(4, usuario.getAvatar());

			
			ps.executeUpdate();
			ps.close();
			
		}finally{
			
				if (ConexionJDBCUtil.getConexion() != null)
					ConexionJDBCUtil.getConexion().close();

		}
		
		
	}
	
	
	// TODO Definir donde se encripta el password. 
	/*
	 * Metodo que modifica al usuario
	 */
	public void modificarUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
	
		String sqlModificarUsuario = "UPDATE t_usuario SET usu_nombre = ?, usu_mail = ?, usu_password = ?, usu_avatar = ? " +
				                     "WHERE usu_id = ?";
		

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlModificarUsuario);
		
		ps.setString(1, usuario.getNombre());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getPassword());
		ps.setBytes(4, usuario.getAvatar());
		ps.setInt(5, usuario.getId());
		
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
	

	public void crearContacto(SolicitudContactoDTO solicitud) throws SQLException{
		
		System.out.println("LLEGUE AL DAO LALALALALALALA");
		
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
	

	public void aprobarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException{
		
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
	public void eliminarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException{
		
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
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
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



	public ArrayList<UsuarioDTO> getContactos(int idUsuario) throws SQLException {
	
		PreparedStatement ps;
		ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
				
		try{
			
			String sqlContactos = "SELECT * FROM t_usuario " +
								  "WHERE usu_id IN (" +
							      "SELECT csol.con_id_usuario_sol id_usu FROM t_contacto csol " +
								  "WHERE csol.con_id_usuario_apr = ? AND csol.con_fecha_aprobacion is not null " +
								  "UNION " +
								  "SELECT capr.con_id_usuario_apr id_usu FROM t_contacto capr " +
								  "WHERE capr.con_id_usuario_sol = ? AND capr.con_fecha_aprobacion is not null) " +
								  "ORDER BY usu_nombre";
			
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlContactos);
	
			ps.setInt(1, idUsuario);
			ps.setInt(2, idUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				UsuarioDTO usuarioRetorno = new UsuarioDTO();
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setEmail(rs.getString("usu_mail"));
				usuarioRetorno.setPassword(rs.getString("usu_password"));
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
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
