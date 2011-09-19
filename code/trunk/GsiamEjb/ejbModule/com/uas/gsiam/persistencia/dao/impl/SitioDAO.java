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

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;
import com.uas.gsiam.persistencia.utiles.Constantes;

public class SitioDAO implements ISitioDAO {

	@Override
	public void buscarSitio(SitioDTO sitioInteres)
			throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarSitio(SitioDTO sitioInteres)
			throws SitioYaExisteExcepcion, SitioExcepcion {
		try {
			PreparedStatement ps = null;

			Point punto = new Point(sitioInteres.getLat(),
					sitioInteres.getLon());
			PGgeometry geom = new PGgeometry(punto);

			String sqlCrearSitio = "INSERT INTO t_sitio (sit_nombre,sit_punto,sit_id_categoria,sit_direccion) VALUES (?,?,?,?)";

			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlCrearSitio);

			
			ps.setString(1, sitioInteres.getNombre());
			ps.setObject(2, geom);
			ps.setInt(3, 1);
			ps.setString(4, sitioInteres.getDireccion());

			ps.execute();
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
		}

	}
	
	private void existeSitio(SitioDTO sitio){
		PreparedStatement ps = null;

		Point punto = new Point(sitio.getLat(),
				sitio.getLon());
		PGgeometry geom = new PGgeometry(punto);
		
		String sqlExisteSitio = "SELECT * FROM t_sitio WHERE sit_punto=?";
		
		try {
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sqlExisteSitio);
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
	
	
	private void eliminarPublicacionesPorSitio(Integer idSitio) throws SitioExcepcion {
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

	@Override
	public void modificarSitio(SitioDTO sitio)
			throws SitioExcepcion {
		
		try{
		PreparedStatement ps = null;

		Point punto = new Point(sitio.getLat(),
				sitio.getLon());
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
		List<SitioDTO> sitios = new ArrayList<SitioDTO>();
		Point punto = new Point(sitio.getLat(),
				sitio.getLon());
		PGgeometry pgeom = new PGgeometry(punto);

		String sql = "select * from t_sitio "
				+ "where sit_punto && 'BOX3D(-30.11082 -57.2068, -35.101934 -55.349121)'::box3d "
				+ "and Distance(sit_punto,GeomFromText(?, -1)) < ?";
		try {
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sql);

			PGgeometry geom;
			ps.setObject(1, pgeom);
			ps.setInt(2, 30);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				geom = (PGgeometry) rs.getObject(2);

				resultado = new SitioDTO();
				// resultado.setDireccion(geom.getGeometry().getValue());
				resultado.setLat(geom.getGeometry().getFirstPoint().getX());
				resultado.setLon(geom.getGeometry().getFirstPoint().getY());
				resultado.setIdSitio(rs.getString(6));
				resultado.setNombre(rs.getString(1));
				resultado.setDireccion(rs.getString(5));
				sitios.add(resultado);
			}
			rs.close();
			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return sitios;
	}

}
