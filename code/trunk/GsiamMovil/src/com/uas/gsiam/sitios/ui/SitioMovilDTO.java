package com.uas.gsiam.sitios.ui;



public class SitioMovilDTO {

	
	public String idSitio;
	public String nombre;
	public String direccion;
	public String lon;
	public String lat;
	
	
	public String getLon() {
		return lon;
	}


	public void setLon(String lon) {
		this.lon = lon;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public SitioMovilDTO(){
		
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
