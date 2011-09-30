package com.uas.gsiam.persistencia.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgresql.PGConnection;
import org.postgresql.PGStatement;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;
import com.uas.gsiam.persistencia.utiles.Constantes;

public class SitioDAO implements ISitioDAO {

	@Override
	public List<SitioDTO> buscarSitio(SitioDTO sitioInteres)
			throws SitioExcepcion {
		ResultSet rs = null;
		PreparedStatement ps = null;
		CategoriaDTO categoria;
		SitioDTO resultado;
		List<SitioDTO> sitios = new ArrayList<SitioDTO>();
		String sqlExisteSitio = "SELECT s.*, c.* FROM t_sitio s, t_categoria c WHERE s.sit_id_categoria=c.cat_id and UPPER(s.sit_nombre) like ?";

		try {

			ps = ConexionJDBCUtil.getConexion()
					.prepareStatement(sqlExisteSitio);
			ps.setString(1, "%" + sitioInteres.getNombre().toUpperCase() + "%");
			rs = ps.executeQuery();
			PGgeometry geom;
			while (rs.next()) {
				resultado = new SitioDTO();
				categoria = new CategoriaDTO();
				geom = (PGgeometry) rs.getObject(2);
				resultado.setLat(geom.getGeometry().getFirstPoint().getX());
				resultado.setLon(geom.getGeometry().getFirstPoint().getY());
				resultado.setIdSitio(rs.getInt(6));
				resultado.setNombre(rs.getString(1));
				resultado.setDireccion(rs.getString(5));
				resultado.setPublicaciones(obtenerPublicacionPorSitio(resultado
						.getIdSitio()));
				categoria.setIdCategoria(rs.getInt(7));
				categoria.setDescripcion(rs.getString(8));
				resultado.setCategoria(categoria);
				sitios.add(resultado);
			}

		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
		} finally {
			try {
				rs.close();
				ps.close();
			} catch (SQLException e) {

				e.printStackTrace();
			}
		}
		return sitios;
	}

	@Override
	public void agregarSitio(SitioDTO sitioInteres)
			throws SitioYaExisteExcepcion, SitioExcepcion {
		PreparedStatement ps = null;
		try {

			Point punto = new Point(sitioInteres.getLat(),
					sitioInteres.getLon());
			PGgeometry geom = new PGgeometry(punto);

			String sqlCrearSitio = "INSERT INTO t_sitio (sit_nombre,sit_punto,sit_id_categoria,sit_direccion) VALUES (?,?,?,?)";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			ps.setString(1, sitioInteres.getNombre().toUpperCase());
			ps.setObject(2, geom);
			ps.setInt(3, sitioInteres.getCategoria().getIdCategoria());
			ps.setString(4, sitioInteres.getDireccion());

			ps.execute();
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	private void existeSitio(SitioDTO sitio) {
		PreparedStatement ps = null;

		Point punto = new Point(sitio.getLat(), sitio.getLon());
		PGgeometry geom = new PGgeometry(punto);

		String sqlExisteSitio = "SELECT * FROM t_sitio WHERE sit_punto=?";

		try {
			ps = ConexionJDBCUtil.getConexion()
					.prepareStatement(sqlExisteSitio);
			ps.setObject(1, geom);
			ResultSet rs = ps.executeQuery();

			rs.next();
		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	@Override
	public void eliminarSitio(Integer idSitio) throws SitioExcepcion {
		try {
			PreparedStatement ps = null;
			eliminarPublicacionesPorSitio(idSitio);
			String sqlCrearSitio = "DELETE FROM t_sitio WHERE sit_id = ?";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			ps.setInt(1, idSitio);

			ps.execute();

			ps.close();

		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		}

	}

	private void eliminarPublicacionesPorSitio(Integer idSitio)
			throws SitioExcepcion {
		try {
			PreparedStatement ps = null;

			String sqlCrearSitio = "DELETE FROM t_publicacion WHERE pub_id_sitio = ?";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			ps.setInt(1, idSitio);

			ps.execute();

			ps.close();

		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		}

	}

	private ArrayList<PublicacionDTO> obtenerPublicacionPorSitio(Integer idSitio)
			throws SQLException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		ArrayList<PublicacionDTO> publicaciones = new ArrayList<PublicacionDTO>();
		PublicacionDTO publicacion;

		String sqlCrearSitio = "SELECT * FROM t_publicacion p, t_usuario u WHERE p.pub_id_usuario=u.usu_id and pub_id_sitio = ?";

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

		ps.setInt(1, idSitio);
		rs = ps.executeQuery();
		if (rs != null) {
			while (rs.next()) {
				publicacion = new PublicacionDTO();
				publicacion.setComentario(rs.getString(2));
				publicacion.setFecha(rs.getDate(1));
				publicacion.setIdPublicacion(rs.getInt(6));
				publicacion.setIdSitio(idSitio);
				publicacion.setIdUsuario(rs.getInt(4));
				publicacion.setPuntaje(rs.getFloat(7));
				publicacion.setNombreUsuario(rs.getString(8));
				publicaciones.add(publicacion);
			}
		}

		ps.close();

		rs.close();

		return publicaciones;
	}

	@Override
	public void modificarSitio(SitioDTO sitio) throws SitioExcepcion {

		try {
			PreparedStatement ps = null;

			Point punto = new Point(sitio.getLat(), sitio.getLon());
			PGgeometry geom = new PGgeometry(punto);

			String sqlCrearSitio = "UPDATE t_sitio SET sit_nombre=?, sit_direccion=?, sit_punto=? where sit_id=?";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			ps.setString(1, sitio.getNombre());
			ps.setString(2, sitio.getDireccion());
			ps.setObject(3, geom);
			ps.setInt(4, new Integer(sitio.getIdSitio()));

			ps.execute();
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		}

	}

	@Override
	public List<SitioDTO> obtenerSitios(SitioDTO sitio) {
		PreparedStatement ps = null;
		SitioDTO resultado = null;
		CategoriaDTO categoria;
		ResultSet rs = null;
		List<SitioDTO> sitios = new ArrayList<SitioDTO>();
		Point punto = new Point(sitio.getLat(), sitio.getLon());
		PGgeometry pgeom = new PGgeometry(punto);

		String sql = "select s.*, c.*, Distance(sit_punto,GeomFromText(?, -1)) as dis "
				+ "from t_sitio s, t_categoria c "
				+ "where s.sit_id_categoria=c.cat_id and s.sit_punto && 'BOX3D(-30.11082 -57.2068, -35.101934 -55.349121)'::box3d "
				+ "and Distance(s.sit_punto,GeomFromText(?, -1)) < ? "
				+ " order by dis";
		try {
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sql);

			PGgeometry geom;
			ps.setObject(1, pgeom);
			ps.setObject(2, pgeom);
			ps.setInt(3, 2);
			rs = ps.executeQuery();

			while (rs.next()) {
				geom = (PGgeometry) rs.getObject(2);
				categoria = new CategoriaDTO();
				resultado = new SitioDTO();

				resultado.setLat(geom.getGeometry().getFirstPoint().getX());
				resultado.setLon(geom.getGeometry().getFirstPoint().getY());
				resultado.setIdSitio(rs.getInt(6));
				resultado.setNombre(rs.getString(1));
				resultado.setDireccion(rs.getString(5));
				resultado.setPublicaciones(obtenerPublicacionPorSitio(resultado
						.getIdSitio()));
				categoria.setIdCategoria(rs.getInt(7));
				categoria.setDescripcion(rs.getString(8));
				resultado.setCategoria(categoria);
				sitios.add(resultado);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sitios;
	}

}
