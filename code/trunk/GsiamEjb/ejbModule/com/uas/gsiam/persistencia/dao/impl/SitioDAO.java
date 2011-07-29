package com.uas.gsiam.persistencia.dao.impl;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.persistencia.dao.ISitioDAO;

public class SitioDAO implements ISitioDAO{

	@Override
	public void buscarSitio(SitioDTO sitioInteres)
			throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void agregarSitio(SitioDTO sitioInteres)
			throws SitioYaExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarSitio(SitioDTO sitioInteres)
			throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

}
