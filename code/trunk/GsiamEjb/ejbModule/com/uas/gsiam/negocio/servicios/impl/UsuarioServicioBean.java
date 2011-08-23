package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.sql.SQLException;

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
			
			if (AbstractFactory.getInstance().getUsuarioDAO().existeUsuario(usuario.getEmail())){
				throw new UsuarioExcepcion(Constantes.ERROR_YA_EXISTE_USUARIO);
			}
			else{
				AbstractFactory.getInstance().getUsuarioDAO().crearUsuario(usuario);
			}
			
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_USUARIO);
		} 
			
	}
	//TODO : deberiamos escribir todos los errores en un log
    
	public String modificarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion {
		
		try {
			
			if (AbstractFactory.getInstance().getUsuarioDAO().existeUsuario(usuario.getEmail())){
				throw new UsuarioExcepcion(Constantes.ERROR_YA_EXISTE_USUARIO);
			}
			else{
				AbstractFactory.getInstance().getUsuarioDAO().modificarUsuario(usuario);
			}
			
			return Constantes.RETURN_OK;
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_MODIFICAR_USUARIO);
		} 
	
	}
	
	
	public String eliminarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion{
			
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().eliminarUsuario(usuario);
			
			return Constantes.RETURN_OK;
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_ELIMINAR_USUARIO);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_ELIMINAR_USUARIO);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_ELIMINAR_USUARIO);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_ELIMINAR_USUARIO);
		}
	
	}
	
	
	
	public String crearSolicitudContacto (SolicitudContacto solicitud) throws UsuarioExcepcion{	
		
		//TODO :  deberia chequear que no exista la solicitud del otro usuario
			
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().crearContacto(solicitud.getIdUsuarioSolicitante(), solicitud.getIdUsuarioAprobador());
			
			//TODO : Falta enviar mail al amigo!!!
			
			return Constantes.RETURN_OK;
			
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_CREAR_CONTACTO);
		}
			
	}
	
	
	
	
	
	
    
}
