package com.uas.gsiam.web.delegate;

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

    public UsuarioDTO login(String email, String pass) {
    	
    		 UsuarioDTO usuario = new UsuarioDTO();
    		 usuario.setEmail(email);
    		 usuario.setPassword(pass);

    	try {
    		 usuario = servicioUsuario.login(usuario);
			
		} catch (UsuarioNoExisteExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return usuario;
    }
	
	
	
}
