package com.uas.gsiam.negocio.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

public class SitioDTO implements Serializable{

	
	private Integer idSitio;
	private String nombre;
	private String direccion;
	private Double lon;
	private Double lat;
	private CategoriaDTO categoria;
	private ArrayList<PublicacionDTO> publicaciones;
	
		
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


	public Integer getIdSitio() {
		return idSitio;
	}


	public void setIdSitio(Integer idSitio) {
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


	public ArrayList<PublicacionDTO> getPublicaciones() {
		return publicaciones;
	}


	public void setPublicaciones(ArrayList<PublicacionDTO> publicaciones) {
		this.publicaciones = publicaciones;
	}


	public CategoriaDTO getCategoria() {
		return categoria;
	}


	public void setCategoria(CategoriaDTO categoria) {
		this.categoria = categoria;
	}
	
	
	
}
