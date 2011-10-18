package com.uas.gsiam.web.servicios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.uas.gsiam.negocio.dto.PublicacionDTO;
import com.uas.gsiam.negocio.dto.SitioDTO;
import com.uas.gsiam.negocio.excepciones.PublicacionExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioExcepcion;
import com.uas.gsiam.negocio.excepciones.SitioYaExisteExcepcion;
import com.uas.gsiam.web.delegate.SitioDelegate;

@Path("/sitios")
public class SitioServicios {

	private SitioDelegate servicio;

	public SitioServicios() {
		servicio = new SitioDelegate();
	}

	@GET
	@Path("/{lat}/{lon}")
	@Produces("application/json")
	public List<SitioDTO> getSitios(@PathParam ("lat") String lat, @PathParam ("lon") String lon){
		List<SitioDTO> listaSitios = new ArrayList<SitioDTO>();
		List<SitioDTO> sitios = servicio.getSitios(lat, lon);
		System.out.println(sitios.size());
		return sitios;
	}
	
	@GET
	@Path("/{nombre}")
	@Produces("application/json")
	public List<SitioDTO> buscarSitios(@PathParam ("nombre") String nombre){
		
		List<SitioDTO> sitios=null;
		try {
			sitios = servicio.buscarSitios(nombre);
		} catch (SitioExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return sitios;
	}
	
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
		
			e.printStackTrace();
		} catch (SitioExcepcion e) {
			System.out.println("prueba de error");
			//throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
			//return Response.status(Status.INTERNAL_SERVER_ERROR);
			
		}
		return builder.type(MediaType.APPLICATION_JSON).build();//status(Status.INTERNAL_SERVER_ERROR).build();
		//return Response.status(200).build().toString();
		
	}
	
	@DELETE
	@Path("/eliminar/{sitio}")
	@Produces("application/json")
	@Consumes("application/json")
    public Response eliminarSitio(@PathParam ("sitio") String sitio) {
			
		
		try {
			servicio.eliminarSitio(new Integer(sitio));
		}  catch (SitioExcepcion e) {
			return Response.status(500).build();
			
		}
		
		return Response.ok().build();
		
		
	}
	
	@PUT
	@Path("/modificar")
	@Produces("application/json")
	@Consumes("application/json")
    public Response modificarSitio(@BadgerFish SitioDTO sitio) {
			
		
		try {
			servicio.modificarSitio(sitio);
		}  catch (SitioExcepcion e) {
			return Response.status(500).build();
			
		}
		
		return Response.ok().build();
		
		
	}
	
	@POST
	@Path("/publicar")
	@Produces("application/json")
	@Consumes("application/json")
    public Response publicar(@BadgerFish PublicacionDTO publicacion) {
		
		try {
			publicacion.setFecha(new Date());
			servicio.crearPublicacion(publicacion);
		} catch (PublicacionExcepcion e) {
			return Response.status(500).build();
			
		}
		return Response.ok().build();
		
	}
	
}

