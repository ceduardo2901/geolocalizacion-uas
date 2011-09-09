package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioNoExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.servicios.SitioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;
import com.uas.gsiam.persistencia.utiles.Constantes;

@Stateless(name="SitioServicio")
public class SitioServicioBean implements SitioServicio{

	
	public SitioServicioBean() {
        
    	
    }
	
	@Override
	public void crearSitio(SitioDTO sitioInteres) throws SitioYaExisteExcepcion, SitioExcepcion {
		
		
		try {
			AbstractFactory.getInstance().getSitioDAO().agregarSitio(sitioInteres);
		} catch (IOException e) {
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
			
		} catch (IllegalAccessException e) {
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
		} catch (ClassNotFoundException e) {
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
			
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

	@Override
	public void crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion {
		
		try {
			AbstractFactory.getInstance().getPublicacionDAO().crearPublicacion(publicacion);
		} catch (IOException e) {
			throw new PublicacionExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		} catch (IllegalAccessException e) {
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		} catch (ClassNotFoundException e) {
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		}
	}

}
