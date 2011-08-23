package com.uas.gsiam.negocio.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;



public class UsuarioDTO implements Serializable{

	// TODO: Como almacenamos la foto del usuario??

	
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String email;
	private String password;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}


	private String token;

	public UsuarioDTO() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
