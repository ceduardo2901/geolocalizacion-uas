package com.uas.gsiam.negocio.dto;

import java.io.Serializable;

public class CategoriaDTO implements Serializable{

	private Integer idCategoria;
	private String descripcion;
	
	public CategoriaDTO(){
		
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}
