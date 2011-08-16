package com.uas.gsiam.web.delegate;

import javax.ejb.EJBContext;

import com.uas.gsiam.web.sl.ServiceLocator;
import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;

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
	
    public void crearUsuario(String email, String pass, String nombre) {
    	
    	UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setEmail(email);
		userDTO.setNombre(nombre);
		userDTO.setPassword(pass);
	
		servicioUsuario.crearUsuario(userDTO);
		
}
    
    
	
}
