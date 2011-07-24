package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;

/**
 * Session Bean implementation class UsuarioServicio
 */
@Stateless(mappedName = "usuario-ejb")
@LocalBean
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
	
    
    
}
