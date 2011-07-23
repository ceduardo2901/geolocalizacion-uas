package com.uas.gsiam.persistencia.test;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.UsuarioNoExisteExcepcion;
import com.uas.gsiam.persistencia.dao.impl.UsuarioDAO;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		System.out.println("#### Arranca la cosa.....");
		
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setEmail("pepe@gmail.com");
		userDTO.setNombre("Pedro Petero");
		userDTO.setPassword("pass");
		UsuarioDAO dao = new UsuarioDAO();
		
		
// Login...
		
		UsuarioDTO Utest;
		try {
			Utest = dao.login(userDTO);
			System.out.println("user id: " + Utest.getId());
			System.out.println("user nombre: " + Utest.getNombre());
			System.out.println("user fecha: " + Utest.getFechaNacimiento());
		} catch (UsuarioNoExisteExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

		
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
