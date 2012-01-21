package com.uas.gsiam.web.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.jboss.resteasy.specimpl.ResponseBuilderImpl;

import com.uas.gsiam.negocio.dto.CategoriaDTO;
import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.web.delegate.SitioDelegate;

/**
 * Esta clase es la responsable de exponer los servicios asociados a los sitios
 * para que sean accedidos por los clientes externos
 * 
 * @author Antonio
 * 
 */
@Path("/sitios")
public class SitioServicios {

	private SitioDelegate servicio;

	public SitioServicios() {
		servicio = new SitioDelegate();
	}

	/**
	 * Este servicio retorna la lista de sitios a un radio determinado del punto
	 * geografico ingresado determinado por la latitud y longuitud
	 * 
	 * @param lat
	 *            latitud
	 * @param lon
	 *            longuitud
	 * @return Devuelve la lista de sitios correspondientes
	 */
	@GET
	@Path("/{lat}/{lon}")
	@Produces("application/json")
	public List<SitioDTO> getSitios(@PathParam("lat") String lat,
			@PathParam("lon") String lon) {
		List<SitioDTO> sitios = new ArrayList<SitioDTO>();
		try {
			sitios = servicio.getSitios(lat, lon);
		} catch (SitioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);

		}

		return sitios;
	}

	/**
	 * Este metodo permite buscar un sitio por su nombre o por su identificador
	 * 
	 * @param id
	 *            Identificador del sitio
	 * @param nombre
	 *            Nombre del sitio
	 * @return Retorna todos los sitios que corresponden con el filtro ingresado
	 */
	@GET
	@Path("/sitio/{id}/{nombre}")
	@Produces("application/json")
	public List<SitioDTO> buscarSitios(@PathParam("id") String id,
			@PathParam("nombre") String nombre) {
		SitioDTO sitio = new SitioDTO();
		if (!id.isEmpty()) {
			sitio.setIdSitio(Integer.valueOf(id));
		}
		if (!nombre.isEmpty() && !nombre.equals(" ")) {
			sitio.setNombre(nombre);
		}
		List<SitioDTO> sitios = null;
		try {
			sitios = servicio.buscarSitios(sitio);
		} catch (SitioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		return sitios;
	}

	/**
	 * Crea un sitio en el sistema con los valores correspondientes al parametro
	 * ingresado
	 * 
	 * @param sitio
	 *            Sitio a crear
	 * @return Retorna codigo de html OK si se crep correctamente sitio en caso
	 *         contrario retorna un error 500 con el mensaje correspondiente
	 */
	@POST
	@Path("/agregar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response crearSitio(@BadgerFish SitioDTO sitio) {
		ResponseBuilder builder = Response.ok();

		try {
			servicio.crearSitio(sitio);
			builder.status(Status.OK);
		} catch (SitioYaExisteExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.CONFLICT);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		} catch (SitioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);

		}
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();

	}

	/**
	 * Elimina un sitio en el sistema con el identificador ingresado
	 * 
	 * @param sitio
	 *            Identificador del sitio a Eliminar
	 * @return Retorna codigo de html OK si se elimino correctamente el sitio en
	 *         caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
	@DELETE
	@Path("/eliminar/{sitio}")
	@Produces("application/json")
	@Consumes("application/json")
	public Response eliminarSitio(@PathParam("sitio") String sitio) {
		ResponseBuilder builder = Response.ok();
		try {
			servicio.eliminarSitio(new Integer(sitio));
		} catch (SitioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);

		}
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();

	}

	/**
	 * Metodo que modifica un sitio de interes con los valores ingresados en el
	 * paramentro correspondiente
	 * 
	 * @param sitio
	 *            Valores a modificar del sitio
	 * @return Retorna codigo de html OK si se modifico correctamente el sitio
	 *         en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
	@PUT
	@Path("/modificar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response modificarSitio(@BadgerFish SitioDTO sitio) {
		ResponseBuilder builder = Response.ok();
		try {
			servicio.modificarSitio(sitio);
		} catch (SitioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);

		}

		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();

	}

	/**
	 * Crea una publicación en el sistema para un sitio
	 * 
	 * @param publicacion
	 *            Publicación a crear
	 * @return Retorna codigo de html OK si se creo correctamente la publicación
	 *         en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
	@POST
	@Path("/publicar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response publicar(@BadgerFish PublicacionDTO publicacion) {
		ResponseBuilder builder = Response.ok();
		try {
			servicio.crearPublicacion(publicacion);
		} catch (PublicacionExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);

		}
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();

	}

	/**
	 * Metodo que retorna las categorias de sitios del sistema
	 * 
	 * @return Retorna la lista de categorias que se desplegaran en el sistema
	 */
	@GET
	@Path("/categorias")
	@Produces("application/json")
	public ArrayList<CategoriaDTO> getCategorias() {

		ArrayList<CategoriaDTO> listaCategorias = null;

		try {
			listaCategorias = servicio.getCategorias();

		} catch (SitioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		return listaCategorias;

	}

}
