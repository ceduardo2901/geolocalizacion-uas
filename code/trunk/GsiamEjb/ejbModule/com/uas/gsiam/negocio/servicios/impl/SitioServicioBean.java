package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

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

	public SitioServicioBean() {

	}

	@Override
	public void crearSitio(SitioDTO sitioInteres)
			throws SitioYaExisteExcepcion, SitioExcepcion {

		try {
			AbstractFactory.getInstance().getSitioDAO()
					.agregarSitio(sitioInteres);
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
	public void eliminarSitio(SitioDTO sitio) throws SitioExcepcion {
		try {
			ISitioDAO sitioDao = AbstractFactory.getInstance().getSitioDAO();
			if (sitioDao.usuarioCreadorSitio(sitio)) {
				sitioDao.eliminarSitio(sitio.getIdSitio());
			} else {
				throw new SitioExcepcion(Constantes.ERROR_USUARIO_NO_AUTORIZADO);
			}
		} catch (IOException e) {
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		} catch (IllegalAccessException e) {
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		} catch (ClassNotFoundException e) {
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_ELIMINAR_SITIO);
		}

	}

	@Override
	public void modificarSitio(SitioDTO sitio) throws SitioExcepcion {

		try {
			
			ISitioDAO sitioDao = AbstractFactory.getInstance().getSitioDAO();
			if (sitioDao.usuarioCreadorSitio(sitio)) {
				sitioDao.modificarSitio(sitio);
			} else {
				throw new SitioExcepcion(Constantes.ERROR_USUARIO_NO_AUTORIZADO);
			}
		} catch (IOException e) {
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		} catch (IllegalAccessException e) {
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		} catch (ClassNotFoundException e) {
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_MODIFICAR_SITIO);
		}
	}

	@Override
	public List<SitioDTO> obtenerSitios(SitioDTO sitio) throws SitioExcepcion {
		List<SitioDTO> sitios = null;
		try {
			sitios = AbstractFactory.getInstance().getSitioDAO()
					.obtenerSitios(sitio);
		} catch (IOException e) {
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		} catch (IllegalAccessException e) {
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		} catch (ClassNotFoundException e) {
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_LISTA_SITIO);
		}
		return sitios;
	}

	@Override
	public int crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion {

		try {
			return AbstractFactory.getInstance().getPublicacionDAO().crearPublicacion(publicacion);
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

	@Override
	public List<SitioDTO> buscarSitios(SitioDTO sitio) throws SitioExcepcion {
		List<SitioDTO> sitios = null;

		try {
			sitios = AbstractFactory.getInstance().getSitioDAO()
					.buscarSitio(sitio);
		} catch (IOException e) {
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
		} catch (IllegalAccessException e) {
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
		} catch (ClassNotFoundException e) {
			throw new SitioExcepcion(Constantes.ERROR_BUSCAR_SITIO);
		}

		return sitios;

	}

	@Override
	public ArrayList<CategoriaDTO> getCategorias() throws SitioExcepcion {

		try {

			return AbstractFactory.getInstance().getSitioDAO().getCategorias();

		} catch (IOException e) {
			throw new SitioExcepcion(Constantes.ERROR_COMUNICACION_BD);
		} catch (InstantiationException e) {
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		} catch (IllegalAccessException e) {
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		} catch (ClassNotFoundException e) {
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		} catch (SQLException e) {
			throw new SitioExcepcion(Constantes.ERROR_CARGAR_CATEGORIAS);
		}

	}

}
