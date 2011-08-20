package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;

import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.SolicitudContacto;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;
import com.uas.gsiam.persistencia.utiles.Constantes;

/**
 * Session Bean implementation class UsuarioServicio
 */
@Stateless(name="UsuarioServicio")
public class UsuarioServicioBean implements UsuarioServicio {

    /**
     * Default constructor. 
     */
    public UsuarioServicioBean() {
        // TODO Auto-generated constructor stub
    }

    
    public UsuarioDTO login (UsuarioDTO usuario) throws UsuarioNoExisteExcepcion{
		UsuarioDTO user=null;
		try {
			//FacebookTemplate facebook = new FacebookTemplate(usuario.getToken());
			//List<String> friends = facebook.friendOperations().getFriendIds();
//			FacebookClient facebook = new DefaultFacebookClient(usuario.getToken());
//			Connection<User> myFriends = facebook.fetchConnection("me/friends", User.class);
			user = AbstractFactory.getInstance().getUsuarioDAO().login(usuario);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return user;
	
	}
	
	
	public void crearUsuario (UsuarioDTO usuario) throws UsuarioExcepcion{
		
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().crearUsuario(usuario);
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CPOMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CPOMUNICACION_BD);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CPOMUNICACION_BD);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CPOMUNICACION_BD);
		}
			
	}
	
    
	public void modificarUsuario (UsuarioDTO usuario) {
		
		try {
			AbstractFactory.getInstance().getUsuarioDAO().modificarUsuario(usuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void eliminarUsuario (UsuarioDTO usuario) {
			
		try {
			AbstractFactory.getInstance().getUsuarioDAO().eliminarUsuario(usuario);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	
	
	public void crearSolicitudContacto (SolicitudContacto solicitud) {	
		
		//TODO :  deberia chequear que no exista la solicitud del otro usuario
			
		try {
			AbstractFactory.getInstance().getUsuarioDAO().crearContacto(solicitud.getIdUsuarioSolicitante(), solicitud.getIdUsuarioAprobador());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	
	
	
	
	
    
}
