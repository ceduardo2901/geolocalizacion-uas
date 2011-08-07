package com.uas.gsiam.web.servicios;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.types.User;
import com.uas.gsiam.web.delegate.UsuarioDelegate;

/**
 * Servlet implementation class ServletPrueba
 */
@WebServlet("/ServletPrueba")
public class ServletPrueba extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
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

//		FacebookClient facebook = new DefaultFacebookClient("FbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnnFbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnnFbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnnFbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnnFbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnnFbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnnFbL4xGph6Y4DoFm9sHa2PZTPAAdlt35iFLZqryFNDCM.eyJpdiI6IjkyRWtCQ2wyUE5JVk0ybVV2R0tMLXcifQ.jHsbxyx5PUI_SZraEFypNfyJ1SunFNGaILLyI6aT2-T4Pc8f9q5sKxgbg0KTOzHj9yL53K-QkQkLOsyS3KKiLYmHck63x-298-8GQmWgK7yKl3AKWfevzV5aPkt_NRnn");
//		User yo = facebook.fetchObject("me", User.class);
//		Connection<User> myFriends = facebook.fetchConnection("me/friends", User.class);
//		UsuarioDelegate delegate = new UsuarioDelegate();
//		
//	    delegate.crearUsuario("m@gmail.com", "123", "Pedro");
		
	
	}

	

}
