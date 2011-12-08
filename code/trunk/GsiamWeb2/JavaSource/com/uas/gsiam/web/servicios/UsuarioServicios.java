package com.uas.gsiam.web.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.uas.gsiam.negocio.dto.PosicionUsuarioDTO;
import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.utiles.Constantes;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

@Path("/usuarios")
public class UsuarioServicios {

	private UsuarioDelegate servicio;

	public UsuarioServicios() {
		servicio = new UsuarioDelegate();
	}

	@GET
	@Path("/login/{email}/{pass}")
	@Produces("application/json")
	public UsuarioDTO login(@PathParam("email") String email,
			@PathParam("pass") String pass) {
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail(email);
		user.setPassword(pass);

		try {
			user = servicio.login(user);

		} catch (UsuarioNoExisteExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.NOT_FOUND);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		} catch (UsuarioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		return user;
	}

	@POST
	@Path("/agregar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response crearUsuario(@BadgerFish UsuarioDTO usuario) {

		ResponseBuilder builder = Response.ok();
		try {
			servicio.crearUsuario(usuario);
			builder.status(Status.OK);

		} catch (UsuarioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();
	}


	@POST
	@Path("/modificar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response modificarUsuario(@BadgerFish UsuarioDTO usuario) {
		
		ResponseBuilder builder = Response.ok();
		try {
			servicio.modificarUsuario(usuario);

		}catch (UsuarioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);

		}

		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();
	}

	@GET
	@Path("/amigos/{id}")
	@Produces("application/json")
	public List<UsuarioDTO> getAmigos(@PathParam("id") int id) {

		List<UsuarioDTO> listaAmigos = new ArrayList<UsuarioDTO>();

		try {
			listaAmigos = servicio.getAmigos(id);
		} catch (UsuarioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		return listaAmigos;

	}

	@GET
	@Path("/usuarios/{id}/{nombre}")
	@Produces("application/json")
	public List<UsuarioDTO> getUsuarios(@PathParam("id") String id,
			@PathParam("nombre") String nombre) {

		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();

		try {
			listaUsuarios = servicio.getUsuarios(Integer.parseInt(id), nombre);
		} catch (UsuarioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}
		return listaUsuarios;

	}

	@POST
	@Path("/agregarsolicitud")
	@Produces("application/json")
	@Consumes("application/json")
	public Response crearSolicitudContacto(
			@BadgerFish SolicitudContactoDTO solicitud) {
		
		ResponseBuilder builder = Response.ok();
		try {

			servicio.crearSolicitudContacto(solicitud);

		} catch (UsuarioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}
		
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();
	}

	@POST
	@Path("/aceptarsolicitud")
	@Produces("application/json")
	@Consumes("application/json")
	public Response aceptarSolicitud(@BadgerFish SolicitudContactoDTO solicitud) {

		ResponseBuilder builder = Response.ok();
		try {
			servicio.responderSolicitudContacto(solicitud,
					Constantes.ACEPTAR_SOLICITUD);

		} catch (UsuarioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}
		
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();
	}

	@POST
	@Path("/rechazarsolicitud")
	@Produces("application/json")
	@Consumes("application/json")
	public Response rechazarSolicitud(@BadgerFish SolicitudContactoDTO solicitud) {
		
		ResponseBuilder builder = Response.ok();
		try {

			servicio.responderSolicitudContacto(solicitud,
					Constantes.RECHAZAR_SOLICITUD);

		} catch (UsuarioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}
		
		builder.type(MediaType.APPLICATION_JSON);
		return builder.build();
	}

	@GET
	@Path("/solicitudesenviadas/{id}")
	@Produces("application/json")
	public List<UsuarioDTO> getSolicitudesEnviadasPendientes(
			@PathParam("id") int id) {

		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();

		try {
			listaUsuarios = servicio.getSolicitudesEnviadasPendientes(id);
		} catch (UsuarioExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaUsuarios;

	}

	@GET
	@Path("/solicitudesrecibidas/{id}")
	@Produces("application/json")
	public List<UsuarioDTO> getSolicitudesRecibidasPendientes(
			@PathParam("id") int id) {

		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();

		try {
			listaUsuarios = servicio.getSolicitudesRecibidasPendientes(id);
		} catch (UsuarioExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return listaUsuarios;

	}

	@GET
	@Path("/invitar/{direcciones}/{nombre}")
	@Produces("application/json")
	public String enviarInvitaciones(
			@PathParam("direcciones") String direcciones,
			@PathParam("nombre") String nombre) {

		try {

			// TODO: Solucion parche, ver como hacemos cuando venga una lista..
			ArrayList<String> dir = new ArrayList<String>();
			dir.add(direcciones);

			servicio.enviarInvitaciones(dir, nombre);
			return Constantes.RETURN_OK;

		} catch (UsuarioExcepcion e) {
			return e.getMensaje();
		}

	}

	@POST
	@Path("/actualizarPosicion")
	@Produces("application/json")
	@Consumes("application/json")
	public String actualizarPosicionUsuario(
			@BadgerFish PosicionUsuarioDTO posUsuario) {

		try {
			servicio.actualizarPosicionUsuario(posUsuario);

			return Constantes.RETURN_OK;

		} catch (UsuarioExcepcion e) {
			return e.getMensaje();

		}

	}

}
