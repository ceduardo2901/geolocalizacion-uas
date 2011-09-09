package com.uas.gsiam.persistencia.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.persistencia.dao.IPublicacionDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;
import com.uas.gsiam.persistencia.utiles.Constantes;

public class PublicacionDAO implements IPublicacionDAO {

	@Override
	public void crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion {

		PreparedStatement ps;

		String sqlCrearUsuario = "INSERT INTO t_publicacion (pub_fecha, pub_comentario, pub_id_usuario, pub_id_sitio) "
				+ "VALUES (?, ?, ?, ?)";

		try {
			ps = ConexionJDBCUtil.getConexion().prepareStatement(
					sqlCrearUsuario);
			ps.setDate(1, new Date(publicacion.getFecha().getTime()));
			ps.setString(2, publicacion.getComentario());
			ps.setString(3, publicacion.getIdUsuario());
			ps.setString(4, publicacion.getIdSitio());
			ps.executeUpdate();
			ps.close();
		} catch (SQLException e) {
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		}

		
	}

}
