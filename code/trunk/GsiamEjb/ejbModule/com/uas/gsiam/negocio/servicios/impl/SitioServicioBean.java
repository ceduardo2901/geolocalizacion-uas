package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.negocio.servicios.SitioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;
import com.uas.gsiam.persistencia.dao.ISitioDAO;
import com.uas.gsiam.persistencia.utiles.Constantes;

@Stateless(name = "SitioServicio")
public class SitioServicioBean implements SitioServicio {

	private static final  Logger logger = LoggerFactory.getLogger(SitioServicioBean.class);
		
	public SitioServicioBean() {

	}

	@Override
	public void crearSitio(SitioDTO sitioInteres)
			throws SitioYaExisteExcepcion, SitioExcepcion {
		logger.info("********** void crearSitio(SitioDTO sitioInteres) ************");
		try {
			AbstractFactory.getInstance().getSitioDAO()
					.agregarSitio(sitioInteres);
			logger.debug("El sitio creado fue: "+sitioInteres.getNombre());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);

		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);

		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CREAR_SITIO);

		}

	}

	@Override
	public void eliminarSitio(SitioDTO sitio) throws SitioExcepcion {
		logger.info("********** eliminarSitio(SitioDTO sitio) ****************");
		try {
			ISitioDAO sitioDao = AbstractFactory.getInstance().getSitioDAO();
			if (sitioDao.usuarioCreadorSitio(sitio)) {
				sitioDao.eliminarSitio(sitio.getIdSitio());
				logger.debug("El sitio eliminado es: "+sitio.getIdSitio());
			} else {
				logger.warn(Constantes.ERROR_USUARIO_NO_AUTORIZADO);
				throw new SitioExcepcion(Constantes.ERROR_USUARIO_NO_AUTORIZADO);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		}

	}

	@Override
	public void modificarSitio(SitioDTO sitio) throws SitioExcepcion {
		logger.info("********** void modificarSitio(SitioDTO sitio) ****************");
		try {
			
			ISitioDAO sitioDao = AbstractFactory.getInstance().getSitioDAO();
			if (sitioDao.usuarioCreadorSitio(sitio)) {
				sitioDao.modificarSitio(sitio);
				logger.debug("El sitio modificado fue: "+sitio.getIdSitio());
			} else {
				logger.warn(Constantes.ERROR_USUARIO_NO_AUTORIZADO);
				throw new SitioExcepcion(Constantes.ERROR_USUARIO_NO_AUTORIZADO);
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		}
	}

	@Override
	public List<SitioDTO> obtenerSitios(SitioDTO sitio) throws SitioExcepcion {
		List<SitioDTO> sitios = null;
		logger.info("********* List<SitioDTO> obtenerSitios(SitioDTO sitio) **************");
		
		try {
			sitios = AbstractFactory.getInstance().getSitioDAO()
					.obtenerSitios(sitio);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		}
		return sitios;
	}

	@Override
	public int crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion {
		logger.info("************ int crearPublicacion(PublicacionDTO publicacion) **************");
		try {
			return AbstractFactory.getInstance().getPublicacionDAO().crearPublicacion(publicacion);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new PublicacionExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new PublicacionExcepcion(Constantes.ERROR_CREAR_PUBLICACION);
		}
	}

	@Override
	public List<SitioDTO> buscarSitios(SitioDTO sitio) throws SitioExcepcion {
		List<SitioDTO> sitios = null;
		logger.info("************ List<SitioDTO> buscarSitios(SitioDTO sitio) **************");
		try {
			sitios = AbstractFactory.getInstance().getSitioDAO()
					.buscarSitio(sitio);
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
		}

		return sitios;

	}

	@Override
	public ArrayList<CategoriaDTO> getCategorias() throws SitioExcepcion {
		logger.info("************ ArrayList<CategoriaDTO> getCategorias() **************");
		try {

			return AbstractFactory.getInstance().getSitioDAO().getCategorias();

		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		}

	}

}
