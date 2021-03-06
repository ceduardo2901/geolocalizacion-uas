package com.uas.gsiam.web.servicios;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.DefaultWebRequestor;
import com.restfb.FacebookClient;
import com.restfb.WebRequestor;
import com.restfb.types.NamedFacebookType;
import com.restfb.types.User;
import com.restfb.util.StringUtils;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

@Path("/usuario")
public class Login {
	
	private UsuarioDelegate servicio;
	
	public Login(){
		servicio = new UsuarioDelegate();
	}
	
	@GET
	@Path("/login/{token}")
	@Produces("application/json")
    public UsuarioDTO login(@PathParam("token") String token) {
		UsuarioDTO user = new UsuarioDTO();
		user.setToken(token);
		WebRequestor d = new  DefaultWebRequestor();
		
		FacebookClient facebook = new DefaultFacebookClient(token);
		User yo = facebook.fetchObject("me", User.class);
		
		
		
		Connection<User> myFriends = facebook.fetchConnection("me/friends", User.class);
		
			user = servicio.login(token);
		 
		
		System.out.println(user.getEmail());
		return user;
    }
}
