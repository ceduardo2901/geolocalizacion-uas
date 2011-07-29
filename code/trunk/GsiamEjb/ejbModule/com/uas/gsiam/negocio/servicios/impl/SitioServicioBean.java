package com.uas.gsiam.negocio.servicios.impl;

import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.SitioServicio;

@Stateless(name="SitioServicio")
public class SitioServicioBean implements SitioServicio{

	
	public SitioServicioBean() {
        
    	
    }
	
	@Override
	public void agregarSitio(SitioDTO sitio) throws SitioNoExisteExcepcion {
		
		
	}

	@Override
	public void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarSitio(SitioDTO sitio) throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

}
