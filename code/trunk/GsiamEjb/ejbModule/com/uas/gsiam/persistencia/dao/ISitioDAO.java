package com.uas.gsiam.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

public interface ISitioDAO {
	
	/**
	 * 
	 * @param sitio
	 * @return
	 * @throws SQLException
	 */
	List<SitioDTO> obtenerSitios(SitioDTO sitio) throws SQLException;

	List<SitioDTO> buscarSitio(SitioDTO sitioInteres)throws SitioExcepcion;
	
	void agregarSitio(SitioDTO sitioInteres)throws SitioYaExisteExcepcion, SitioExcepcion;
	
	void eliminarSitio(Integer idSitio) throws SitioExcepcion;
	
	void modificarSitio(SitioDTO sitioInteres) throws SitioExcepcion;
	
	ArrayList<CategoriaDTO> getCategorias() throws SQLException;
}
