package com.uas.gsiam.persistencia.dao.impl;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.postgis.PGgeometry;
import org.postgis.Point;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.dao.IUsuarioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;


public class UsuarioDAO implements IUsuarioDAO {
	
	
	public UsuarioDTO login(UsuarioDTO usuario) throws UsuarioNoExisteExcepcion{
		
		PreparedStatement ps;
		UsuarioDTO usuarioRetorno = null;
				
		try {
			
			String sqlLogin = "SELECT * FROM t_usuario u " +
	                          "WHERE u.usu_mail = ? AND u.usu_password = ? AND u.usu_activo = 1";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlLogin);

			ps.setString(1, usuario.getEmail());
			ps.setString(2, usuario.getPassword());
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				usuarioRetorno = new UsuarioDTO();
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
	        						  "WHERE u.usu_mail = ? AND u.usu_activo = 1";
			
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
	        						  "WHERE u.usu_id = ? AND u.usu_activo = 1";
			
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
	
	
	public ArrayList<UsuarioDTO> getUsuarios(int id, String nombre) throws SQLException{
		
		PreparedStatement ps;
		ArrayList<UsuarioDTO> listaRetorno = new ArrayList<UsuarioDTO>();
		
		try{

			String sqlUsuariosNoAmigos = "SELECT todos.*, soli.flag " +
									    "FROM (SELECT * " + 
										  "FROM t_usuario u " + 
										  "WHERE UPPER(u.usu_nombre) like ? AND u.usu_id <> ? AND u.usu_activo = 1 AND " + 
											"u.usu_id NOT IN (SELECT CASE  " +
													 "WHEN c.con_id_usuario_sol = ? THEN c.con_id_usuario_apr " +
													 "WHEN c.con_id_usuario_apr = ? THEN c.con_id_usuario_sol " +
													 "END " +
													 "FROM t_contacto c " + 
													 "WHERE (c.con_id_usuario_sol = ? OR c.con_id_usuario_apr = ?) AND " + 
														"c.con_fecha_aprobacion is not null)) todos  " +
										"LEFT OUTER JOIN  (SELECT CASE " +
													 "WHEN c.con_id_usuario_sol = ? THEN c.con_id_usuario_apr " +
													 "WHEN c.con_id_usuario_apr = ? THEN c.con_id_usuario_sol " +
												         "END id, " +
												         "CASE " +
													 "WHEN c.con_id_usuario_sol = ? THEN 1 " +
													 "WHEN c.con_id_usuario_apr = ? THEN 2 " +
												         "END flag , c.* " +
													"FROM t_contacto c  " +
													"WHERE (c.con_id_usuario_sol = ? OR c.con_id_usuario_apr = ?) AND " +
													"c.con_fecha_aprobacion is null) soli ON (soli.id = todos.usu_id) " +
									  "ORDER BY todos.usu_nombre ";
			
	
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlUsuariosNoAmigos);
			
			ps.setString(1, "%" + nombre.toUpperCase() +"%");
			ps.setInt(2, id);
			ps.setInt(3, id);
			ps.setInt(4, id);
			ps.setInt(5, id);
			ps.setInt(6, id);
			ps.setInt(7, id);
			ps.setInt(8, id);
			ps.setInt(9, id);
			ps.setInt(10, id);
			ps.setInt(11, id);
			ps.setInt(12, id);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				
				UsuarioDTO usuario = new UsuarioDTO();
				usuario.setId(rs.getInt("usu_id"));
				usuario.setNombre(rs.getString("usu_nombre"));	
				usuario.setEmail(rs.getString("usu_mail"));
				usuario.setPassword(rs.getString("usu_password"));
				usuario.setAvatar(rs.getBytes("usu_avatar"));
				
				switch (rs.getInt("flag")) {
				case 1:
					usuario.setSolicitudEnviada(true);
					usuario.setSolicitudRecibida(false);
					break;
				case 2:
					usuario.setSolicitudEnviada(false);
					usuario.setSolicitudRecibida(true);
					break;
				default:
					usuario.setSolicitudEnviada(false);
					usuario.setSolicitudRecibida(false);
					break;
				}
				
				
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
		
			String sqlCrearUsuario = "INSERT INTO t_usuario (usu_nombre, usu_mail, usu_password, usu_avatar, usu_activo) " +
					                 "VALUES (?, ?, ?, ?, ?)";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario);
			
			ps.setString(1, usuario.getNombre());
			ps.setString(2, usuario.getEmail());
			ps.setString(3, usuario.getPassword());
			ps.setBytes(4, usuario.getAvatar());
			ps.setInt(5, 1);

			
			ps.executeUpdate();
			ps.close();
			
		}finally{
			
				if (ConexionJDBCUtil.getConexion() != null)
					ConexionJDBCUtil.getConexion().close();

		}
		
		
	}
	
	
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
	
	
	public void actualizarPosicionUsuario(PosicionUsuarioDTO posUsuario) throws SQLException{
		
		PreparedStatement ps;
	
		String sqlActualizarPocision = "UPDATE t_usuario SET usu_ubicacion = ?, usu_ubicacion_fecha = ? " +
				                       "WHERE usu_id = ?";
		

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlActualizarPocision);
		
		Point punto = new Point(posUsuario.getLat(), posUsuario.getLon());
		PGgeometry geom = new PGgeometry(punto);

		java.util.Date hoy = new java.util.Date();
		java.sql.Timestamp sqlHoy = new java.sql.Timestamp(hoy.getTime());
		
		ps.setObject(1, geom);
		ps.setTimestamp(2, sqlHoy);
		ps.setInt(3, posUsuario.getIdUsuario());
		
		ps.executeUpdate();
		
		ps.close();

		
	}
	
	
	
	/*
	 * Metodo que desactiva usuario
	 */
	public void desactivarUsuario(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
					
		String sqlDesactivarUsuario = "UPDATE t_usuario SET usu_activo = ? " +
								      "WHERE usu_id = ?";

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlDesactivarUsuario);
		
		ps.setInt(1, 0);
		ps.setInt(2, usuario.getId());
		
		ps.executeUpdate();
		
		ps.close();
 
		
	}
	
	/*
	 * Elimina las solicitudes del usuario
	 */
	public void eliminarContactos(UsuarioDTO usuario) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlEliminarContactos = "DELETE FROM t_contacto " +
						              "WHERE con_id_usuario_sol = ? OR con_id_usuario_apr = ?";

		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlEliminarContactos);
		
		
		ps.setInt(1, usuario.getId());
		ps.setInt(2, usuario.getId());
		
		ps.executeUpdate();
					
		ps.close();
 
		
	}
	

	public void crearContacto(SolicitudContactoDTO solicitud) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlCrearContacto = "INSERT INTO t_contacto (con_id_usuario_sol, con_id_usuario_apr, con_fecha_solicitud, con_fecha_aprobacion) " +
				                  "VALUES (?, ?, ?, ?)";
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearContacto);
		
		java.util.Date hoy = new java.util.Date();
		java.sql.Date sqlHoy = new java.sql.Date(hoy.getTime());

		
		ps.setInt(1, solicitud.getIdUsuarioSolicitante());
		ps.setInt(2, solicitud.getIdUsuarioAprobador());
		ps.setDate(3, sqlHoy);
		ps.setDate(4, null);
		
		ps.executeUpdate();
					
		ps.close();
 
		
	}
	

	public void aprobarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException{
		
		PreparedStatement ps;

		String sqlAprobarContacto = "UPDATE t_contacto SET con_fecha_aprobacion = ? " +
				                    "WHERE con_id_usuario_sol = ? AND con_id_usuario_apr = ?";
	
		
		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlAprobarContacto);
				
		java.util.Date hoy = new java.util.Date();
		java.sql.Date sqlHoy = new java.sql.Date(hoy.getTime());
		
		ps.setDate(1, sqlHoy);
		ps.setInt(2, solicitud.getIdUsuarioSolicitante());
		ps.setInt(3, solicitud.getIdUsuarioAprobador());
		
		ps.executeUpdate();
					
		ps.close();
 
		
	}
	
	
	// TODO : Ver como resolver cuando uno se elimina una amistad ya estando aprobada.... en caso que lo implementemos
	public void eliminarSolicitudContacto(SolicitudContactoDTO solicitud) throws SQLException{
		
		PreparedStatement ps;
			
		String sqlEliminarContacto = "DELETE FROM t_contacto WHERE con_id_usuario_sol=? AND con_id_usuario_apr = ?";

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlEliminarContacto);
			
		ps.setInt(1, solicitud.getIdUsuarioSolicitante());
		ps.setInt(2, solicitud.getIdUsuarioAprobador());
		
		ps.executeUpdate();
		
		ps.close();

	}
	
	
	
	
	public ArrayList<UsuarioDTO> getSolicitudesRecibidasPendientes(int idUsuario) throws SQLException{
		
		PreparedStatement ps;
		ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
				
		try{
			
			String sqlSolicitudesUsuarios = "SELECT u.* FROM t_contacto c, t_usuario u " +
											"WHERE c.con_id_usuario_apr = ? AND " +
											"u.usu_id = c.con_id_usuario_sol AND " +
											"c.con_fecha_aprobacion is null";
			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlSolicitudesUsuarios);
	
			ps.setInt(1, idUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				UsuarioDTO usuarioRetorno = new UsuarioDTO();
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setEmail(rs.getString("usu_mail"));
				usuarioRetorno.setPassword(rs.getString("usu_password"));
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
				usuarioRetorno.setSolicitudEnviada(false);
				usuarioRetorno.setSolicitudRecibida(true);
				
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


	public ArrayList<UsuarioDTO> getSolicitudesEnviadasPendientes(int idUsuario) throws SQLException{
		
		PreparedStatement ps;
		ArrayList<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
				
		try{
			
			String sqlSolicitudesUsuarios = "SELECT u.* FROM t_contacto c, t_usuario u " +
											"WHERE c.con_id_usuario_sol = ? AND " +
											"u.usu_id = c.con_id_usuario_apr AND " +
											"c.con_fecha_aprobacion is null";

			
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlSolicitudesUsuarios);
	
			ps.setInt(1, idUsuario);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				UsuarioDTO usuarioRetorno = new UsuarioDTO();
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setEmail(rs.getString("usu_mail"));
				usuarioRetorno.setPassword(rs.getString("usu_password"));
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
				usuarioRetorno.setSolicitudEnviada(true);
				usuarioRetorno.setSolicitudRecibida(false);
				
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
			
			UsuarioDTO usuarioRetorno;
			PosicionUsuarioDTO posicion;
			PGgeometry geom;
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
				
				usuarioRetorno = new UsuarioDTO();
				usuarioRetorno.setNombre(rs.getString("usu_nombre"));	
				usuarioRetorno.setId(rs.getInt("usu_id"));
				usuarioRetorno.setEmail(rs.getString("usu_mail"));
				usuarioRetorno.setPassword(rs.getString("usu_password"));
				usuarioRetorno.setAvatar(rs.getBytes("usu_avatar"));
				
				geom = (PGgeometry) rs.getObject("usu_ubicacion");
				
				if (geom != null){
					posicion = new PosicionUsuarioDTO();
					posicion.setLat(geom.getGeometry().getFirstPoint().getX());
					posicion.setLon(geom.getGeometry().getFirstPoint().getY());
					
					posicion.setFechaActualizacion(rs.getTimestamp("usu_ubicacion_fecha"));
					posicion.setIdUsuario(rs.getInt("usu_id"));
					usuarioRetorno.setPosicion(posicion);
				}
				
				
				
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
