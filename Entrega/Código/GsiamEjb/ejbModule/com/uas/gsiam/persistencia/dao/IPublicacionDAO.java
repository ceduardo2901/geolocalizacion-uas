package com.uas.gsiam.persistencia.dao;

import com.uas.gsiam.negocio.dto.PublicacionDTO;

import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;

public interface IPublicacionDAO {

	/**
	 * Crea una publicación sobre un sitio en el sistema
	 * 
	 * @param publicacion
	 *            Publiación a crear
	 * @throws PublicacionExcepcion
	 * @return Retorna el id de la publicacion
	 * 
	 */
	int crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion;
}
