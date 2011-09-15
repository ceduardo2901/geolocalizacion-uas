package com.uas.gsiam.web.servicios;

import javax.xml.bind.annotation.XmlRootElement;

import com.uas.gsiam.negocio.dto.SitioDTO;

@XmlRootElement
public class ServicioSitioDTO {

	public String idSitio;
	public String nombre;
	public String direccion;
	public String lon;
	public String lat;

	public ServicioSitioDTO() {

	}

	public ServicioSitioDTO(SitioDTO sitio) {
		this.idSitio = sitio.getIdSitio();
		this.nombre = sitio.getNombre();
		this.direccion = sitio.getDireccion();
		this.lat = sitio.getLat().toString();
		this.lon = sitio.getLon().toString();
	}

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
