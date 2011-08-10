package com.uas.gsiam.negocio.servicios;

import java.util.List;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

@Remote
public interface SitioServicio {

	static final String SERVICE_ADDRESS = "java:global/Gsiam/GsiamEjb/SitioServicio";
	
	void agregarSitio(SitioDTO sitioInteres) throws SitioYaExisteExcepcion;
	
	void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion;
	
	void modificarSitio(SitioDTO sitioInteres) throws SitioNoExisteExcepcion;
	
	List<SitioDTO> obtenerSitios(SitioDTO sitio);
}
