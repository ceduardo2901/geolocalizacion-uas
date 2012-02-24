package com.uas.gsiam.persistencia.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.servicios.impl.SitioServicioBean;
import com.uas.gsiam.persistencia.dao.IPublicacionDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;
import com.uas.gsiam.persistencia.utiles.Constantes;

public class PublicacionDAO implements IPublicacionDAO {

	private static final Logger logger = LoggerFactory
			.getLogger(SitioServicioBean.class);

	@Override
	public int crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion {
		
		PreparedStatement ps=null;
		ResultSet rs = null;
		int idPublicacion = 0;

		String sqlCrearUsuario = "INSERT INTO t_publicacion (pub_fecha, pub_comentario, pub_foto ,pub_id_usuario, pub_id_sitio, pub_puntaje) "
				+ "VALUES (?, ?, ? ,?, ?, ?)";

		try {
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearUsuario, Statement.RETURN_GENERATED_KEYS);
			ps.setDate(1, new Date(publicacion.getFecha().getTime()));
			ps.setString(2, publicacion.getComentario());
			ps.setBytes(3, publicacion.getFoto());
			ps.setInt(4, publicacion.getIdUsuario().intValue());
			ps.setInt(5, publicacion.getIdSitio());
			ps.setFloat(6, publicacion.getPuntaje());
			
			int affectedRows = ps.executeUpdate(); 
	        if (affectedRows == 0) { 
	        	logger.warn(Constantes.ERROR_CREAR_PUBLICACION);
	            throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
	        } 
	 
	        rs = ps.getGeneratedKeys(); 
	        if (rs.next()) { 
	        	idPublicacion = rs.getInt("pub_id"); 
	        } else { 
	        	logger.warn(Constantes.ERROR_CREAR_PUBLICACION);
	            throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
	        } 
			
			rs.close();
			ps.close();
			
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		}finally {
			try {
				ps.close();
			} catch (SQLException e) {
				logger.error(e.getMessage(),e);
			}
		}
		
		return idPublicacion;
		
	}
}
