package com.uas.gsiam.test.dto;

import java.io.Serializable;

public class CategoriaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idCategoria;
	private String descripcion;
	private String imagen;
	
	private Integer idGrupo;
	private String descripcionGrupo;
	private String imagenGrupo;
	
	
	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public Integer getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getDescripcionGrupo() {
		return descripcionGrupo;
	}

	public void setDescripcionGrupo(String descripcionGrupo) {
		this.descripcionGrupo = descripcionGrupo;
	}

	public String getImagenGrupo() {
		return imagenGrupo;
	}

	public void setImagenGrupo(String imagenGrupo) {
		this.imagenGrupo = imagenGrupo;
	}

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
