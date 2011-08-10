package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.util.List;

import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.negocio.servicios.SitioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;

@Stateless(name="SitioServicio")
public class SitioServicioBean implements SitioServicio{

	
	public SitioServicioBean() {
        
    	
    }
	
	@Override
	public void agregarSitio(SitioDTO sitioInteres) throws SitioYaExisteExcepcion {
		
		
		try {
			AbstractFactory.getInstance().getSitioDAO().agregarSitio(sitioInteres);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void eliminarSitio(String idSitio) throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void modificarSitio(SitioDTO sitio) throws SitioNoExisteExcepcion {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SitioDTO> obtenerSitios(SitioDTO sitio) {
		List<SitioDTO> sitios=null;
		try {
			sitios = AbstractFactory.getInstance().getSitioDAO().obtenerSitios(sitio);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sitios;
	}

}
