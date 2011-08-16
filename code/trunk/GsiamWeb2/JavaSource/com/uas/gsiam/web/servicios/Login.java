package com.uas.gsiam.web.servicios;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

@Path("/usuario")
public class Login {
	
	private UsuarioDelegate servicio;
	
	public Login(){
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
}
