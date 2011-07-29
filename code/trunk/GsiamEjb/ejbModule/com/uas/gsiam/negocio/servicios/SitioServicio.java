package com.uas.gsiam.negocio.servicios;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;

@Remote
public interface SitioServicio {

	void agregarSitio(SitioDTO sitio) throws SitioNoExisteExcepcion;
	
	void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion;
	
	void modificarSitio(SitioDTO sitio) throws SitioNoExisteExcepcion;
}
