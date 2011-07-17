package com.uas.gsiam.web.servicios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.servicios.IUsuarioServicio;
import com.uas.gsiam.negocio.servicios.impl.UsuarioServicio;
import com.uas.gsiam.web.AircraftTypes;

@Path("/usuario")
public class Login {
	
	private IUsuarioServicio servicio;
	
	public Login(){
		servicio = new UsuarioServicio();
	}
	
	@GET
	@Path("/login")
	@Produces({MediaType.APPLICATION_JSON})
    public UsuarioDTO login(@QueryParam("email") String email, @QueryParam("pass") String pass) {
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail(email);
		user.setPassword(pass);
		user = servicio.login(user);
		return user;
    }
}
