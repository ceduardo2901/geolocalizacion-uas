package com.uas.gsiam.persistencia.dao;

import com.uas.gsiam.negocio.dto.PublicacionDTO;

import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;

public interface IPublicacionDAO {

	void crearPublicacion(PublicacionDTO publicacion)throws PublicacionExcepcion;
}
