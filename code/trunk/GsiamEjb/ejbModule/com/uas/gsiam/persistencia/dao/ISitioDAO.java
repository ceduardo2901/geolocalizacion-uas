package com.uas.gsiam.persistencia.dao;

import java.util.List;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

public interface ISitioDAO {
	
	List<SitioDTO> obtenerSitios(SitioDTO sitio);

	List<SitioDTO> buscarSitio(SitioDTO sitioInteres)throws SitioExcepcion;
	
	void agregarSitio(SitioDTO sitioInteres)throws SitioYaExisteExcepcion, SitioExcepcion;
	
	void eliminarSitio(Integer idSitio) throws SitioExcepcion;
	
	void modificarSitio(SitioDTO sitioInteres) throws SitioExcepcion;
}
