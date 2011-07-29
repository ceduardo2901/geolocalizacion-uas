package com.uas.gsiam.persistencia.dao;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

public interface ISitioDAO {

	void buscarSitio(SitioDTO sitioInteres)throws SitioNoExisteExcepcion;
	
	void agregarSitio(SitioDTO sitioInteres)throws SitioYaExisteExcepcion;
	
	void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion;
	
	void modificarSitio(SitioDTO sitioInteres) throws SitioNoExisteExcepcion;
}
