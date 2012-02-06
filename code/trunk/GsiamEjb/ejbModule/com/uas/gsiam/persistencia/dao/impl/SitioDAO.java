package com.uas.gsiam.persistencia.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgis.PGgeometry;
import org.postgis.Point;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
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

		StringBuilder sqlExisteSitio = new StringBuilder(
				"SELECT s.*, c.* FROM t_sitio s, t_categoria c WHERE s.sit_id_categoria=c.cat_id ");
		if (sitioInteres.getIdSitio() != null) {
			if (sitioInteres.getIdSitio().doubleValue() > 0) {
				sqlExisteSitio.append("and s.sit_id=?");
			}
		}

		if (sitioInteres.getNombre() != null) {
			String nombre = sitioInteres.getNombre();
			if (!nombre.equals(" ")) {
				sqlExisteSitio.append("and upper(s.sit_nombre) like ?");
			}
		}
		try {

			ps = ConexionJDBCUtil.getConexion().prepareStatement(
					sqlExisteSitio.toString());
			if (sitioInteres.getNombre() != null) {
				ps.setString(1, "%" + sitioInteres.getNombre().toUpperCase()
						+ "%");
			} else {
				ps.setInt(1, sitioInteres.getIdSitio());
			}
			rs = ps.executeQuery();
			PGgeometry geom;
			while (rs.next()) {
				resultado = new SitioDTO();
				categoria = new CategoriaDTO();
				geom = (PGgeometry) rs.getObject("sit_punto");
				resultado.setLat(geom.getGeometry().getFirstPoint().getX());
				resultado.setLon(geom.getGeometry().getFirstPoint().getY());
				resultado.setIdSitio(rs.getInt("sit_id"));
				resultado.setNombre(rs.getString("sit_nombre"));
				resultado.setDireccion(rs.getString("sit_direccion"));
				resultado.setTelefono(rs.getString("sit_telefono"));
				resultado.setWeb(rs.getString("sit_web"));
				resultado.setPublicaciones(obtenerPublicacionPorSitio(resultado
						.getIdSitio()));
				categoria.setIdCategoria(rs.getInt("cat_id"));
				categoria.setDescripcion(rs.getString("cat_descripcion"));
				categoria.setImagen(rs.getString("cat_imagen"));
				resultado.setCategoria(categoria);
				sitios.add(resultado);
			}

		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
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

			String sqlCrearSitio = "INSERT INTO t_sitio (sit_nombre,sit_punto,sit_id_categoria,sit_direccion,sit_telefono,sit_web,sit_id_usuario) VALUES (?,?,?,?,?,?,?)";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			ps.setString(1, sitioInteres.getNombre());
			ps.setObject(2, geom);
			ps.setInt(3, sitioInteres.getCategoria().getIdCategoria());
			ps.setString(4, sitioInteres.getDireccion());
			ps.setString(5, sitioInteres.getTelefono());
			ps.setString(6, sitioInteres.getWeb());
			ps.setInt(7, sitioInteres.getIdUsuario());
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
				publicacion.setComentario(rs.getString("pub_comentario"));
				publicacion.setFecha(rs.getDate("pub_fecha"));
				publicacion.setFoto(rs.getBytes("pub_foto"));
				publicacion.setIdPublicacion(rs.getInt("pub_id"));
				publicacion.setIdSitio(idSitio);
				publicacion.setIdUsuario(rs.getInt("pub_id_usuario"));
				publicacion.setPuntaje(rs.getFloat("pub_puntaje"));
				publicacion.setNombreUsuario(rs.getString("usu_nombre"));
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

			String sqlCrearSitio = "UPDATE t_sitio SET sit_nombre=?, sit_direccion=?, sit_telefono=?, sit_web=?, sit_punto=? where sit_id=?";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			ps.setString(1, sitio.getNombre());
			ps.setString(2, sitio.getDireccion());
			ps.setString(3, sitio.getTelefono());
			ps.setString(4, sitio.getWeb());
			ps.setObject(5, geom);
			ps.setInt(6, new Integer(sitio.getIdSitio()));

			ps.execute();
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		}

	}

	@Override
	public List<SitioDTO> obtenerSitios(SitioDTO sitio) throws SQLException {
		PreparedStatement ps = null;
		SitioDTO resultado = null;
		CategoriaDTO categoria;
		ResultSet rs = null;
		List<SitioDTO> sitios = new ArrayList<SitioDTO>();
		Point punto = new Point(sitio.getLat(), sitio.getLon());
		PGgeometry pgeom = new PGgeometry(punto);

		String sql = "select s.*, c.* " + "from t_sitio s, t_categoria c "
				+ "where s.sit_id_categoria=c.cat_id "
				+ "and ST_DWithin(s.sit_punto,GeomFromText(?),?)"
				+ " order by ST_Distance(sit_punto,GeomFromText(?))";

		ps = ConexionJDBCUtil.getConexion().prepareStatement(sql);

		PGgeometry geom;
		ps.setObject(1, pgeom);
		ps.setDouble(2, 0.01);
		ps.setObject(3, pgeom);

		rs = ps.executeQuery();

		while (rs.next()) {

			categoria = new CategoriaDTO();
			resultado = new SitioDTO();
			geom = (PGgeometry) rs.getObject("sit_punto");
			resultado.setLat(geom.getGeometry().getFirstPoint().getX());
			resultado.setLon(geom.getGeometry().getFirstPoint().getY());
			resultado.setIdSitio(rs.getInt("sit_id"));
			resultado.setNombre(rs.getString("sit_nombre"));
			resultado.setDireccion(rs.getString("sit_direccion"));
			resultado.setTelefono(rs.getString("sit_telefono"));
			resultado.setWeb(rs.getString("sit_web"));
			resultado.setPublicaciones(obtenerPublicacionPorSitio(resultado
					.getIdSitio()));
			categoria.setIdCategoria(rs.getInt("cat_id"));
			categoria.setDescripcion(rs.getString("cat_descripcion"));
			categoria.setImagen(rs.getString("cat_imagen"));
			resultado.setCategoria(categoria);
			sitios.add(resultado);

		}

		ps.close();
		rs.close();

		return sitios;
	}

	public ArrayList<CategoriaDTO> getCategorias() throws SQLException {

		PreparedStatement ps;
		ArrayList<CategoriaDTO> listaRetorno = new ArrayList<CategoriaDTO>();

		try {

			String sqlCategorias = "SELECT * "
					+ "FROM t_categoria cat, t_grupo_categoria grupo "
					+ "WHERE cat.cat_grp_id = grupo.grp_cat_id "
					+ "ORDER BY cat.cat_grp_id, cat.cat_descripcion";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCategorias);

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				CategoriaDTO categoria = new CategoriaDTO();

				categoria.setIdCategoria(rs.getInt("cat_id"));
				categoria.setDescripcion(rs.getString("cat_descripcion"));
				categoria.setImagen(rs.getString("cat_imagen"));

				categoria.setIdGrupo(rs.getInt("grp_cat_id"));
				categoria.setDescripcionGrupo(rs
						.getString("grp_cat_descripcion"));
				categoria.setImagenGrupo(rs.getString("grp_cat_imagen"));

				listaRetorno.add(categoria);
			}

			rs.close();
			ps.close();

		} finally {

			if (ConexionJDBCUtil.getConexion() != null)
				ConexionJDBCUtil.getConexion().close();

		}

		return listaRetorno;

	}

	@Override
	public boolean usuarioCreadorSitio(SitioDTO sitio) throws SQLException {
		boolean result;
		try {

			PreparedStatement ps;
			String sql = "select * from t_sitio where sit_id=? and sit_id_usuario=?";
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sql);
			ps.setInt(1, sitio.getIdSitio());
			ps.setInt(2, sitio.getIdUsuario());
			ResultSet rs = ps.executeQuery();
			result = rs.next();
			rs.close();
			ps.close();
		} finally {

			if (ConexionJDBCUtil.getConexion() != null)
				ConexionJDBCUtil.getConexion().close();

		}
		return result;
	}

}
