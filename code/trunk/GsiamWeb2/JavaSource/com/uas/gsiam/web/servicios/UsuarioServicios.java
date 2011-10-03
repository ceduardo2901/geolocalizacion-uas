package com.uas.gsiam.web.servicios;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.uas.gsiam.negocio.dto.SolicitudContacto;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.persistencia.utiles.Constantes;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

@Path("/usuarios")
public class UsuarioServicios {
	
	private UsuarioDelegate servicio;
	
	public UsuarioServicios(){
		servicio = new UsuarioDelegate();
	}
	
	@GET
	@Path("/login/{email}/{pass}")
	@Produces("application/json")
    public UsuarioDTO login(@PathParam("email") String email, @PathParam("pass") String pass) {
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail(email);
		user.setPassword(pass);

		System.out.println(user.getEmail());
		user = servicio.login(user);
		 
		
		System.out.println(user.getEmail());
		return user;
    }
	
	
	@POST
	@Path("/agregar")
	@Produces("application/json")
	@Consumes("application/json")
    public String crearUsuario(@BadgerFish UsuarioDTO usuario) {
		
		
    try {
			
		servicio.crearUsuario(usuario);
		
		return Constantes.RETURN_OK;
			
		} catch (UsuarioExcepcion e) {
			return e.getMensaje();
		}
		
		 
		
    }
		
	
	@POST
	@Path("/editar")
	@Produces("application/json")
	@Consumes("application/json")
    public String editarUsuario(@BadgerFish UsuarioDTO usuario) {


		try {

			servicio.modificarUsuario(usuario);

			return Constantes.RETURN_OK;

		} catch (UsuarioExcepcion e) {
			return e.getMensaje();
		} catch (Exception e) {
			return e.getMessage();
		}
//TODO Esta bien agarrar el exception aca??
		
    }
	
	
	@GET
	@Path("/amigos/{id}")
	@Produces("application/json")
	public List<UsuarioDTO> getAmigos(@PathParam ("id") int id){
			
		
		List<UsuarioDTO> listaAmigos = new ArrayList<UsuarioDTO>();
		
			try {
				listaAmigos = servicio.getAmigos(id);
			} catch (UsuarioExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO definir como enviar el error en caso de

			System.out.println(listaAmigos.size());

			return listaAmigos;
		
	}
	
	
	@GET
	@Path("/usuarios/{nombre}")
	@Produces("application/json")
	public List<UsuarioDTO> getUsuarios(@PathParam ("nombre") String nombre){
			
		
		List<UsuarioDTO> listaUsuarios = new ArrayList<UsuarioDTO>();
		
			try {
				listaUsuarios = servicio.getUsuarios(nombre);
			} catch (UsuarioExcepcion e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//TODO definir como enviar el error en caso de

			System.out.println(listaUsuarios.size());

			return listaUsuarios;
		
	}
	
	
	
	@POST
	@Path("/agregarsolicitud/{ids}/{ida}")
	@Produces("application/json")
    public String creaSolicitudContacto(@PathParam ("ids") int idSolicitante, @PathParam ("ida") int idAprobador) {
		
		
    try {
			
        SolicitudContacto solicitud = new SolicitudContacto();
        solicitud.setIdUsuarioSolicitante(idSolicitante);
        solicitud.setIdUsuarioAprobador(idAprobador);
        
		servicio.crearSolicitudContacto(solicitud);
		
		return Constantes.RETURN_OK;
			
		} catch (UsuarioExcepcion e) {
			return e.getMensaje();
		}
		
		 
		
    }
	
	
	
}
