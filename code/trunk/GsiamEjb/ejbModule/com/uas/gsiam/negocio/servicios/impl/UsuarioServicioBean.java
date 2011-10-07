package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.ejb.Stateless;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
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
    
	public void modificarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion {
		
		try {
			
			UsuarioDTO userBD = AbstractFactory.getInstance().getUsuarioDAO().getUsuario(usuario.getId());
			
			if (userBD != null){
				
				if(userBD.getEmail().equalsIgnoreCase(usuario.getEmail())){
					
					AbstractFactory.getInstance().getUsuarioDAO().modificarUsuario(usuario);
				}
				else{
					
					if (AbstractFactory.getInstance().getUsuarioDAO().existeUsuario(usuario.getEmail())){
						throw new UsuarioExcepcion(Constantes.ERROR_YA_EXISTE_USUARIO);
					}
					else{
						AbstractFactory.getInstance().getUsuarioDAO().modificarUsuario(usuario);
					}
				}
				
				
			}
			
			
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
	
	
	public void eliminarUsuario (UsuarioDTO usuario) throws UsuarioExcepcion{
			
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().eliminarUsuario(usuario);
			
			
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
	
	
	
	public void crearSolicitudContacto (SolicitudContactoDTO solicitud) throws UsuarioExcepcion{	
		
		//TODO :  deberia chequear que no exista la solicitud del otro usuario
			
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().crearContacto(solicitud);
			
			//TODO : Falta enviar mail al amigo!!!
			//TODO : ver como solucionamos lo de las notificaciones.
			
			
			
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
	
	
	public void responderSolicitudContacto (SolicitudContactoDTO solicitud, boolean respuesta) throws UsuarioExcepcion {
		
		try {
			
			if (respuesta){
				// Se acepta la solicitud
				AbstractFactory.getInstance().getUsuarioDAO().aprobarSolicitudContacto(solicitud);
			}
			else{
				// Se rechaza la solicitud
				AbstractFactory.getInstance().getUsuarioDAO().eliminarSolicitudContacto(solicitud);
			}
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RESPONDER_SOLICITUD);
		}
		
	}
	
	
	public ArrayList<UsuarioDTO> getSolicitudesContactosPendientes (int id) throws UsuarioExcepcion{	
			
		try {
			
			return AbstractFactory.getInstance().getUsuarioDAO().getSolicitudesRecibidasPendientes(id);
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_SOLICITUDES);
		}
			
	}
	
	
	public ArrayList<UsuarioDTO> getAmigos (int idUsuario) throws UsuarioExcepcion{	
		
		try {
			
			return AbstractFactory.getInstance().getUsuarioDAO().getContactos(idUsuario);
			
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_CONTACTOS);
		}
			
	}


	public ArrayList<UsuarioDTO> getUsuarios(int id, String nombre) throws UsuarioExcepcion {
		
		try {
		
			return AbstractFactory.getInstance().getUsuarioDAO().getUsuarios(id, nombre);
		
		} catch (IOException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_COMUNICACION_BD);
			
		} catch (InstantiationException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);
			
		} catch (IllegalAccessException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);
			
		} catch (ClassNotFoundException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);
			
		} catch (SQLException e) {
			throw new UsuarioExcepcion(Constantes.ERROR_RECUPERAR_USUARIOS);
		}
	}

	
    
}
