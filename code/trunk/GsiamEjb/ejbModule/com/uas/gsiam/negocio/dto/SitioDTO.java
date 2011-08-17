package com.uas.gsiam.negocio.dto;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

public class SitioDTO implements Serializable{

	
	private String idSitio;
	private String nombre;
	private String direccion;
	private Double lon;
	private Double lat;
	
		
	public Double getLon() {
		return lon;
	}


	public void setLon(Double lon) {
		this.lon = lon;
	}


	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}


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
