package com.uas.gsiam.web.servicios;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.negocio.servicios.UsuarioServicio;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

/**
 * Servlet implementation class ServletPrueba
 */
@WebServlet("/ServletPrueba")
public class ServletPrueba extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	@EJB(beanName="UsuarioServicio")
	private UsuarioServicio usuario;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPrueba() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//UsuarioDelegate delegate = new UsuarioDelegate();
		
		UsuarioDTO user = new UsuarioDTO();
		user.setEmail("mloure@lala.com");
		user.setPassword("lala");
		//user = delegate.login("mloure@lala.com", "lala");
		
		try {
			user = usuario.login(user);
		} catch (UsuarioNoExisteExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(user.getEmail());
		
		
		
	}

	

}
