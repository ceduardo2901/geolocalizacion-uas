package com.uas.gsiam.web.delegate;

import java.util.List;

import com.uas.gsiam.negocio.dto.SolicitudContactoDTO;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioExcepcion;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.web.sl.ServiceLocator;

public class UsuarioDelegate {
	
	private UsuarioServicio servicioUsuario;
	
    public UsuarioDelegate() {
        initialLoadBean();
        
    }

    public void initialLoadBean() {
        try {
            this.servicioUsuario =
                    (UsuarioServicio)ServiceLocator.getBean(UsuarioServicio.SERVICE_ADDRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public UsuarioDTO login(UsuarioDTO usuario) {
    	
    		 
    	try {
    		 usuario = servicioUsuario.login(usuario);
			
		} catch (UsuarioNoExisteExcepcion e) {
			
			e.printStackTrace();
		}
		return usuario;
    }
	
    
    
    public void crearUsuario(UsuarioDTO usuario) throws UsuarioExcepcion {
    		
    	servicioUsuario.crearUsuario(usuario);
		
	}
    
    public void modificarUsuario(UsuarioDTO usuario) throws UsuarioExcepcion {
		
    	servicioUsuario.modificarUsuario(usuario);
		
	}
    
    public List<UsuarioDTO> getAmigos(int idUsuario) throws UsuarioExcepcion{
    	
    	return servicioUsuario.getAmigos(idUsuario);
    }
	
    
    public List<UsuarioDTO> getUsuarios(int id, String nombre) throws UsuarioExcepcion{
    	
    	return servicioUsuario.getUsuarios(id, nombre);
    }
    
    
    public void crearSolicitudContacto(SolicitudContactoDTO solicitud) throws UsuarioExcepcion{
    	
    	servicioUsuario.crearSolicitudContacto(solicitud);
    }
    
    
   public void responderSolicitudContacto(SolicitudContactoDTO solicitud, int accion) throws UsuarioExcepcion{
    	
    	servicioUsuario.responderSolicitudContacto(solicitud, accion);
    }
    
    
    
}
