package com.uas.gsiam.negocio.servicios;

import java.util.List;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

@Remote
public interface SitioServicio {

	static final String SERVICE_ADDRESS = "java:global/Gsiam/GsiamEjb/SitioServicio";
	
	void crearSitio(SitioDTO sitioInteres) throws SitioYaExisteExcepcion, SitioExcepcion;
	
	void eliminarSitio(Integer idSitio) throws SitioExcepcion;
	
	void modificarSitio(SitioDTO sitioInteres) throws SitioExcepcion;
	
	List<SitioDTO> obtenerSitios(SitioDTO sitio);
	
	List<SitioDTO> buscarSitios(SitioDTO sitio) throws SitioExcepcion;
	
	void crearPublicacion(PublicacionDTO publicacion) throws PublicacionExcepcion;
}
