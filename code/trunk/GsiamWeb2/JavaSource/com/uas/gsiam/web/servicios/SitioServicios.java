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
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;
import org.jboss.security.xacml.core.model.context.StatusType;

import com.sun.xml.ws.encoding.ContentType;
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
	public List<ServicioSitioDTO> getSitios(@PathParam ("lat") String lat, @PathParam ("lon") String lon){
		List<ServicioSitioDTO> listaSitios = new ArrayList<ServicioSitioDTO>();
		List<SitioDTO> sitios = servicio.getSitios(lat, lon);
		for(SitioDTO sitio : sitios){
			listaSitios.add(new ServicioSitioDTO(sitio));
		}
		System.out.println(sitios.size());
		
		return listaSitios;
	}
	
	@POST
	@Path("/agregar")
	@Produces("application/json")
	@Consumes("application/json")
    public Response crearSitio(@BadgerFish SitioDTO sitio) {
		
		try {
			servicio.crearSitio(sitio);
		} catch (SitioYaExisteExcepcion e) {
		
			e.printStackTrace();
		} catch (SitioExcepcion e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
			
		}
		return Response.status(200).type(MediaType.APPLICATION_JSON).build();
		
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

