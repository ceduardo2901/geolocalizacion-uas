package com.uas.gsiam.web.servicios;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.negocio.servicios.impl.UsuarioServicioBean;

@Path("/usuario")
public class Login {
	
	private UsuarioServicio servicio;
	
	public Login(){
		servicio = new UsuarioServicioBean();
	}
	
	@GET
	@Path("/login")
	@Produces({MediaType.APPLICATION_JSON})
    public UsuarioDTO login(@QueryParam("email") String email, @QueryParam("pass") String pass) {
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail(email);
		user.setPassword(pass);
		try {
			user = servicio.login(user);
		} catch (UsuarioNoExisteExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user.getEmail());
		return user;
    }
}
