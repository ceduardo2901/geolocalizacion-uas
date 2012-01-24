package com.uas.gsiam.persistencia.dao;

import com.uas.gsiam.negocio.dto.PublicacionDTO;

import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;

public interface IPublicacionDAO {

	/**
	 * Crea una publicaci�n sobre un sitio en el sistema
	 * 
	 * @param publicacion
	 *            Publiaci�n a crear
	 * @throws PublicacionExcepcion
	 */
	void crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion;
}
