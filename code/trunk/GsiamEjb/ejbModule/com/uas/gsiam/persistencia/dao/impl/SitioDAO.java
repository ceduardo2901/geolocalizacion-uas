package com.uas.gsiam.persistencia.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.postgis.Geometry;
import org.postgis.PGgeometry;
import org.postgresql.geometric.PGpolygon;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.utiles.ConexionJDBCUtil;

public class SitioDAO implements ISitioDAO {

	@Override
	public void buscarSitio(SitioDTO sitioInteres)
			throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public void agregarSitio(SitioDTO sitioInteres)
			throws SitioYaExisteExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public void modificarSitio(SitioDTO sitioInteres)
			throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub

	}

	@Override
	public List<SitioDTO> obtenerSitios(SitioDTO sitio) {
		PreparedStatement ps;
		SitioDTO resultado = null;
		List<SitioDTO> sitios = new ArrayList<SitioDTO>();
		
		String sql = "select the_geom from americas_south_america_uruguay_poi "
				+ "where the_geom && 'BOX3D(-55.2068 -30.11082, -61.6068 -36.62082)'::box3d "
				+ "and Distance(the_geom,GeomFromText('POINT(-58.3068 -33.12082)', -1)) < 100";
		try {
			ps = ConexionJDBCUtil.getConexion().prepareStatement(sql);

//			ps.setDouble(1, sitio.getLat());
//			ps.setDouble(2, sitio.getLon());
			PGgeometry geom;
			ResultSet rs = ps.executeQuery();
			//System.out.println(rs.);
			while (rs.next()) {
				geom = (PGgeometry) rs.getObject(1);
				//.out.println(geom.getValue());
				resultado = new SitioDTO();
				resultado.setDireccion(geom.getValue());
				sitios.add(resultado);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sitios;
	}

}
