package com.uas.gsiam.negocio.dto;

import java.util.Date;

public class PublicacionDTO {

	private String idPublicacion;
	private String comentario;
	private String idUsuario;
	private String idSitio;
	private Date fecha;
	
	public PublicacionDTO(){
		
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdPublicacion() {
		return idPublicacion;
	}
	public void setIdPublicacion(String idPublicacion) {
		this.idPublicacion = idPublicacion;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(String idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getIdSitio() {
		return idSitio;
	}
	public void setIdSitio(String idSitio) {
		this.idSitio = idSitio;
	}
	
	
	
}
