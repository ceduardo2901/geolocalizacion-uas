package com.uas.gsiam.web.sl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.uas.gsiam.negocio.servicios.UsuarioServicio;



/**
 * Clase que representa el patron Service Locator
 * 
 */
public class ServiceLocator {

	private static ServiceLocator instancia;
	private UsuarioServicio usuarioServicio;
	private String jndi;

	
	private ServiceLocator() throws NamingException{
	}
	
	/**
	 * @return La instancia (singleton)
	 * @throws NamingException
	 */
	public static ServiceLocator getInstancia() throws NamingException{
		if(instancia == null)
			instancia = new ServiceLocator();
			
		return instancia;
	}
	
	/**
	 * @return La fachada del EJB de Usuario
	 * @throws NamingException
	 */
	public UsuarioServicio getUsuarioServicio() throws NamingException{
		Context ctx = new InitialContext();
		jndi = "ProyectoEAR/UsuarioServicio/remote";
		usuarioServicio = (UsuarioServicio) ctx.lookup(jndi);
		return usuarioServicio; 
	}
	
	
}
