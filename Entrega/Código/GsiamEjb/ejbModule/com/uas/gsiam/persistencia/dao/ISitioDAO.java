package com.uas.gsiam.persistencia.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;

public interface ISitioDAO {

	/**
	 * /** Obtiene los sitios de interes a un radio determinado de una posición
	 * geografica dada
	 * 
	 * @param sitio
	 *            Posición geografica
	 * @return Retorna todos los sitios de interes que se encuentran a un radio
	 *         determinado de una posición geografica dada
	 * @throws SQLException
	 */
	List<SitioDTO> obtenerSitios(SitioDTO sitio) throws SQLException;

	/**
	 * Busca los sitios con los criterios de busqueda ingresados por paramentro
	 * 
	 * @param sitio
	 *            Filtros que se aplicaran al buscar los sitios de interes
	 * @return Retorna una lista con los sitios encontrados
	 * @throws SitioExcepcion
	 */
	List<SitioDTO> buscarSitio(SitioDTO sitioInteres) throws SitioExcepcion;

	/**
	 * Crea un sitio de interes en el sistema con los datos ingresados
	 * 
	 * @param sitioInteres
	 *            Sitio de interes a crear en el sistema
	 * @throws SitioYaExisteExcepcion
	 * @throws SitioExcepcion
	 */
	void agregarSitio(SitioDTO sitioInteres) throws SitioYaExisteExcepcion,
			SitioExcepcion;

	/**
	 * Elimina un sitio de interes del sistema con el identificador ingresado
	 * 
	 * @param idSitio
	 *            Identificador del sitio de interes a elminar
	 * @throws SitioExcepcion
	 */
	void eliminarSitio(Integer idSitio) throws SitioExcepcion;

	/**
	 * Modifica un sitio de interes del sistema
	 * 
	 * @param sitioInteres
	 *            Datos del sitio a modificar
	 * @throws SitioExcepcion
	 */
	void modificarSitio(SitioDTO sitioInteres) throws SitioExcepcion;

	/**
	 * Este metodo retorna las categorias de sitios de interes que se encuentran
	 * en el sistema
	 * 
	 * @return Lista de categorias del sistema
	 * @throws SQLException
	 */
	ArrayList<CategoriaDTO> getCategorias() throws SQLException;

	/**
	 * Controla que el usuario sea el creador del sitio y asi tenga los permisos
	 * para modificarlo o borrarlo
	 * 
	 * @return Retorna true si el usuario es el creador del sitio, false en caso
	 *         contrario
	 * @throws SQLException
	 */
	boolean usuarioCreadorSitio(SitioDTO sitio) throws SQLException;
}
