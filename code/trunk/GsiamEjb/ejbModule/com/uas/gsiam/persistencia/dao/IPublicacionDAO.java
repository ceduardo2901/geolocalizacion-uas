package com.uas.gsiam.persistencia.dao;

import java.sql.SQLException;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

public interface IPublicacionDAO {

	void crearPublicacion(PublicacionDTO publicacion)throws PublicacionExcepcion;
}
