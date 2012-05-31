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

/**
 * Esta clase es la responsable de exponer los servicios asociados a los
 * Usuarios para que sean accedidos por los clientes externos
 * 
 * @author Martín
 * 
 */
@Path("/usuarios")
public class UsuarioServicios {

	private UsuarioDelegate servicio;

	public UsuarioServicios() {
		servicio = new UsuarioDelegate();
	}

	/**
	 * Metodo que identifica un usuario en el sistema a partir de su email y
	 * contrseña
	 * 
	 * @param email
	 *            Email del usuario
	 * @param pass
	 *            Contraseña del usuario
	 * @return El usuario autenticado en caso correcto. En caso de error el
	 *         metodo tira una WebApplicationException
	 */
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

	/**
	 * Crea un usuario en el sistema
	 * 
	 * @param usuario
	 *            Usuario a crear en el sistema
	 * @return Retorna codigo de html OK si se creo correctamente el usuario en
	 *         caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */

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

	/**
	 * Modifica un usuario del sistema con los nuevos datos ingresados
	 * 
	 * @param usuario
	 *            Datos del usuario a modificar
	 * @return Retorna codigo de html OK si se modifico correctamente el usuario
	 *         en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
	@POST
	@Path("/modificar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response modificarUsuario(@BadgerFish UsuarioDTO usuario) {

		ResponseBuilder builder = Response.ok();
		try {
			servicio.modificarUsuario(usuario);

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

	/**
	 * Este metodo cierra la cuenta de un usuario en el sistema
	 * 
	 * @param usuario
	 *            Identificador de usuario a eliminar del sistema
	 * @return Retorna codigo de html OK si se cerro la cuenta correctamente en
	 *         caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
	@POST
	@Path("/cerrar")
	@Produces("application/json")
	@Consumes("application/json")
	public Response cerrarCuenta(@BadgerFish UsuarioDTO usuario) {

		ResponseBuilder builder = Response.ok();
		try {
			servicio.cerrarCuenta(usuario);

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

	/**
	 * Retorna la lista de amigos del usuario indentificado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de amigos correspondiente
	 */
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

	/**
	 * Retorna todos los usuarios cuyo nombre contiene el nombre ingresado por
	 * parametro que no sean amigos del id ingresado.
	 * 
	 * @param id
	 * @param nombre
	 * @return Retorna la lista de usuarios correspondientes
	 */
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

	/**
	 * Crea una solicitud de Amistad en el sistema
	 * 
	 * @param solicitud
	 *            Solicitud a crear
	 * @return Retorna codigo de html OK si se creo correctamente la solicitud
	 *         en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
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

	/**
	 * Metodo para aceptar una solicitud de amistad
	 * 
	 * @param solicitud
	 *            Solicitud de amistad
	 * @return Retorna codigo de html OK si se acepto correctamente la solicitud
	 *         en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
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

	/**
	 * Metodo para rechazar una solicitud de amistad enviada por un usuario del
	 * sistema
	 * 
	 * @param solicitud
	 *            Solicitud de amistad a rechazar
	 * @return Retorna codigo de html OK si se rechazo correctamente la
	 *         solicitud en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
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

	/**
	 * Obtener todos los usuario con solicitudes enviadas pendientes de procesar
	 * con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 */
	@GET
	@Path("/solicitudesenviadas/{id}")
	@Produces("application/json")
	public List<UsuarioDTO> getSolicitudesEnviadasPendientes(
			@PathParam("id") int id) {

		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();

		try {
			listaUsuarios = servicio.getSolicitudesEnviadasPendientes(id);
		} catch (UsuarioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		return listaUsuarios;

	}

	/**
	 * Obtener todos los usuario con solicitudes recibidas pendientes de
	 * procesar con un identificador dado
	 * 
	 * @param id
	 *            Identificador de usuario
	 * @return Retorna la lista de usuario correspondiente
	 */
	@GET
	@Path("/solicitudesrecibidas/{id}")
	@Produces("application/json")
	public List<UsuarioDTO> getSolicitudesRecibidasPendientes(
			@PathParam("id") int id) {

		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();

		try {
			listaUsuarios = servicio.getSolicitudesRecibidasPendientes(id);
		} catch (UsuarioExcepcion e) {
			ResponseBuilderImpl builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}

		return listaUsuarios;

	}

	/**
	 * Metodo para enviar una invitacion a una persona que no se encuentra en el
	 * sistema
	 * 
	 * @param direccion
	 *            Direccion de emial de la persona a invitar al sistema
	 * @param nombre
	 *            Nombre del usuario del sistema
	 * @return Retorna codigo de html OK si se envio correctamente la invitacion
	 *         en caso contrario retorna un error 500 con el mensaje
	 *         correspondiente
	 */
	@GET
	@Path("/invitar/{email}/{nombre}")
	@Produces("application/json")
	public String enviarInvitaciones(@PathParam("email") String email,
			@PathParam("nombre") String nombre) {

		ResponseBuilder builder = Response.ok();
		try {

			servicio.enviarInvitaciones(email, nombre);

		} catch (UsuarioExcepcion e) {
			builder = new ResponseBuilderImpl();
			builder.status(Response.Status.INTERNAL_SERVER_ERROR);
			builder.entity(e.getMessage());
			Response response = builder.build();
			throw new WebApplicationException(response);
		}
		return "ok";
	}

	/**
	 * Actualiza la posición geografica del usuario
	 * 
	 * @param posUsuario
	 *            Posición geografica del usuario
	 * @return Retorna codigo de html OK si se actalizo correctamente la
	 *         posición geografica en caso contrario retorna un error 500 con el
	 *         mensaje correspondiente
	 */
	@POST
	@Path("/actualizarPosicion")
	@Produces("application/json")
	@Consumes("application/json")
	public Response actualizarPosicionUsuario(
			@BadgerFish PosicionUsuarioDTO posUsuario) {

		ResponseBuilder builder = Response.ok();
		try {
			servicio.actualizarPosicionUsuario(posUsuario);

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

}
