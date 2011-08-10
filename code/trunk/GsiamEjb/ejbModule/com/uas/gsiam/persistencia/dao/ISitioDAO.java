package com.uas.gsiam.persistencia.dao;

import java.util.List;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

public interface ISitioDAO {
	
	List<SitioDTO> obtenerSitios(SitioDTO sitio);

	void buscarSitio(SitioDTO sitioInteres)throws SitioNoExisteExcepcion;
	
	void agregarSitio(SitioDTO sitioInteres)throws SitioYaExisteExcepcion;
	
	void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion;
	
	void modificarSitio(SitioDTO sitioInteres) throws SitioNoExisteExcepcion;
}
