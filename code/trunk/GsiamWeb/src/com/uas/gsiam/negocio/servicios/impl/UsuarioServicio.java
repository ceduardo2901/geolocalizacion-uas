package com.uas.gsiam.negocio.servicios.impl;

import java.io.IOException;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.servicios.IUsuarioServicio;
import com.uas.gsiam.persistencia.AbstractFactory;

public class UsuarioServicio implements IUsuarioServicio{

	
	public void login (UsuarioDTO usuario){
		
		try {
			
			usuario = AbstractFactory.getInstance().getUsuarioDAO().login(usuario);
			
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
	
	}
	
	
	public void crearUsuario (UsuarioDTO usuario){
		
		try {
			
			AbstractFactory.getInstance().getUsuarioDAO().crearUsuario(usuario);
			
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
	
	}
	
	
}
