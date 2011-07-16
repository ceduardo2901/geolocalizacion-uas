package com.uas.gsiam.persistencia.test;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.persistencia.dao.impl.UsuarioDAO;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("#### Arranca la cosa.....");
		
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setEmail("pepe@1gmail.com");
		userDTO.setNombre("Pedro Petero");
		userDTO.setPassword("pass");
		UsuarioDAO dao = new UsuarioDAO();
		
		
// Login...
		
		
		String test = dao.login(userDTO);
		
		System.out.println("Test: " + test);

		
// Create...
		/*
		int cant = dao.crearUsuario(userDTO);
		
		System.out.println("cant: " + cant);
		
		*/
		
		
// Exist...
		/*
		if (dao.existeUsuario(userDTO))
			System.out.println("existe");
		else
			System.out.println("no existe");
		
		System.out.println("#### Termina la cosa.....");
		*/
		
		
		

	}

}
