package com.uas.gsiam.negocio.dto;

import java.io.Serializable;

public class SitioDTO implements Serializable{

	private String idSitio;
	private String nombre;
	private String direccion;
	
	
	public SitioDTO(){
		
	}


	public String getIdSitio() {
		return idSitio;
	}


	public void setIdSitio(String idSitio) {
		this.idSitio = idSitio;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
	
}
