package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;

import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;

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
		//	FacebookClient facebook = new DefaultFacebookClient(usuario.getToken());
			//Connection<User> myFriends = facebook.fetchConnection("me/friends", User.class);
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
	
	
	public void crearUsuario (UsuarioDTO usuario){
		
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().crearUsuario(usuario);
			
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
	
    
	public void modificarUsuario (UsuarioDTO usuario){
		
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
	
	public void eliminarUsuario (UsuarioDTO usuario){
		
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
    
}
