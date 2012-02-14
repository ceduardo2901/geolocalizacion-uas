package com.uas.gsiam.web.delegate;

import java.util.ArrayList;
import java.util.List;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.negocio.servicios.SitioServicio;
import com.uas.gsiam.web.sl.ServiceLocator;

/**
 * Esta clase es la encargada de llamar a los servicios publicados en el ejb
 * SitioServicioBean.
 * 
 * @author Antonio
 * 
 */
public class SitioDelegate {

	private SitioServicio servicioSitio;

	public SitioDelegate() {
		initialLoadBean();
	}

	public void initialLoadBean() {
		try {
			this.servicioSitio = (SitioServicio) ServiceLocator
					.getBean(SitioServicio.SERVICE_ADDRESS);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la lista de sitios a un radio dado de las coordenadas ingresadas
	 * por parametros
	 * 
	 * @param lat
	 *            latitud ingredad
	 * @param lon
	 *            loguitud ingresada
	 * @return la lista de sitios a un radio dado
	 * @throws SitioExcepcion
	 */
	public List<SitioDTO> getSitios(String lat, String lon)
			throws SitioExcepcion {
		SitioDTO sitio = new SitioDTO();
		sitio.setLat(new Double(lat));
		sitio.setLon(new Double(lon));
		return servicioSitio.obtenerSitios(sitio);
	}

	/**
	 * Retorna la lista de sitios que corresponde con los criterios de filtro
	 * ingresados
	 * 
	 * @param sitio
	 *            Este parametro contiene los filtros que se aplicaran para
	 *            buscar los sitios
	 * @return Lista de sitios que machean con los filtros ingresados
	 * @throws SitioExcepcion
	 */
	public List<SitioDTO> buscarSitios(SitioDTO sitio) throws SitioExcepcion {

		return servicioSitio.buscarSitios(sitio);
	}

	/**
	 * Crea un sitio nuevo en el sistema
	 * 
	 * @param sitio
	 *            Sitio nuevo a crear
	 * @throws SitioYaExisteExcepcion
	 * @throws SitioExcepcion
	 */
	public void crearSitio(SitioDTO sitio) throws SitioYaExisteExcepcion,
			SitioExcepcion {

		servicioSitio.crearSitio(sitio);
	}

	/**
	 * Elimina un sitio del sistema
	 * 
	 * @param sitio
	 *            Id del sitio que se desea eliminar y identificador del usuario que eliminara el sitio
	 * @throws SitioExcepcion
	 */
	public void eliminarSitio(SitioDTO sitio) throws SitioExcepcion {

		servicioSitio.eliminarSitio(sitio);
	}

	/**
	 * Modifica un sitio en el sistema
	 * 
	 * @param sitio
	 *            Datos del sitio que se desean eliminar
	 * @throws SitioExcepcion
	 */
	public void modificarSitio(SitioDTO sitio) throws SitioExcepcion {

		servicioSitio.modificarSitio(sitio);
	}

	/**
	 * Este metodo crea una nueva publicacion en el sistema asociada a un sitio
	 * 
	 * @param publicacion
	 *            Publicacion a crear en el sistema
	 * @throws PublicacionExcepcion
	 */
	public int crearPublicacion(PublicacionDTO publicacion)
			throws PublicacionExcepcion {

		return servicioSitio.crearPublicacion(publicacion);
	}

	/**
	 * Metodo que retorna las categorias de sitios del sistema
	 * 
	 * @return Retorna la lista de categorias que se desplegaran en el sistema
	 * @throws SitioExcepcion
	 */
	public ArrayList<CategoriaDTO> getCategorias() throws SitioExcepcion {

		return servicioSitio.getCategorias();
	}

}
