package com.uas.gsiam.negocio.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remote;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

/**
 * Interfaz remoto del ejb encargado de procesar las solicitudes para los sitios
 * 
 * @author Antonio
 * 
 */
@Remote
public interface SitioServicio {

	static final String SERVICE_ADDRESS = "java:global/Gsiam/GsiamEjb/SitioServicio";

	/**
	 * Crea un sitio de interes en el sistema con los datos ingresados
	 * 
	 * @param sitioInteres
	 *            Sitio de interes a crear en el sistema
	 * @throws SitioYaExisteExcepcion
	 * @throws SitioExcepcion
	 */
	void crearSitio(SitioDTO sitioInteres) throws SitioYaExisteExcepcion,
			SitioExcepcion;

	/**
	 * Elimina un sitio de interes del sistema con el identificador ingresado
	 * 
	 * @param sitio
	 *            sitio de interes a elminar
	 * @throws SitioExcepcion
	 */
	void eliminarSitio(SitioDTO sitio) throws SitioExcepcion;

	/**
	 * Modifica un sitio de interes del sistema
	 * 
	 * @param sitioInteres
	 *            Datos del sitio a modificar
	 * @throws SitioExcepcion
	 */
	void modificarSitio(SitioDTO sitioInteres) throws SitioExcepcion;

	/**
	 * Obtiene los sitios de interes a un radio determinado de una posición
	 * geografica dada
	 * 
	 * @param sitio
	 *            Posición geografica
	 * @return Retorna todos los sitios de interes que se encuentran a un radio
	 *         determinado de una posición geografica dada
	 * @throws SitioExcepcion
	 */
	List<SitioDTO> obtenerSitios(SitioDTO sitio) throws SitioExcepcion;

	/**
	 * Busca los sitios con los criterios de busqueda ingresados por paramentro
	 * 
	 * @param sitio
	 *            Filtros que se aplicaran al buscar los sitios de interes
	 * @return Retorna una lista con los sitios encontrados
	 * @throws SitioExcepcion
	 */
	List<SitioDTO> buscarSitios(SitioDTO sitio) throws SitioExcepcion;

	/**
	 * Crea una publicación sobre un sitio en el sistema
	 * 
	 * @param publicacion
	 *            Publiación a crear           
	 * @return Retorna el id de la publicacion
	 * @throws PublicacionExcepcion
	 */
	int crearPublicacion(PublicacionDTO publicacion) throws PublicacionExcepcion;

	/**
	 * Este metodo retorna las categorias de sitios de interes que se encuentran
	 * en el sistema
	 * 
	 * @return Lista de categorias del sistema
	 * @throws SitioExcepcion
	 */
	public ArrayList<CategoriaDTO> getCategorias() throws SitioExcepcion;
}
