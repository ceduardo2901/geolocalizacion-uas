package com.uas.gsiam.web.delegate;

import javax.ejb.EJB;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;

public class UsuarioDelegate {
	
	
	@EJB(beanName="UsuarioServicio")
	private UsuarioServicio servicioUsuario;

    public UsuarioDelegate() {
       
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
