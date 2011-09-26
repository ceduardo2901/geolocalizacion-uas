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

import com.uas.gsiam.negocio.dto.SitioDTO;
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
			
		System.out.println("Legue!!!!!! lalalalalalalalalla");
		System.out.println("Legue!!!!!! lalalalalalalalalla --- id="+id);
		List<UsuarioDTO> listaAmigos = new ArrayList<UsuarioDTO>();
		/*
			listaAmigos = servicio.getAmigos(id);
*/
			UsuarioDTO userDTO = new UsuarioDTO();
			userDTO.setEmail("pepe@gmail.com");
			userDTO.setNombre("Pedro Petero");
			userDTO.setPassword("pass");
			
			listaAmigos.add(userDTO);
			

			System.out.println(listaAmigos.size());

			return listaAmigos;
		
	}
	
	
}
