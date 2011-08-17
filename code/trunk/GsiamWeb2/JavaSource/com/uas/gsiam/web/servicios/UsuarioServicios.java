package com.uas.gsiam.web.servicios;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

@Path("/usuarios")
public class UsuarioServicios {
	
	private UsuarioDelegate servicio;
	
	public UsuarioServicios(){
		servicio = new UsuarioDelegate();
	}
	
	@GET
	@Path("/login/{pass}/{email}")
	@Produces("application/json")
    public UsuarioDTO login(@PathParam("pass") String pass, @PathParam("email") String email) {
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail(email);
		user.setPassword(pass);

		
		user = servicio.login(user);
		 
		
		System.out.println(user.getEmail());
		return user;
    }
	
	
	@POST
	@Path("/agregar/{usuarioDto}")
	@Produces("application/json")
	@Consumes("application/json")
    public void crearUsuario(@PathParam("usuarioDto") UsuarioDTO usuario) {
		
		
		servicio.crearUsuario(usuario);
		 
		
    }
	
	
	
}
