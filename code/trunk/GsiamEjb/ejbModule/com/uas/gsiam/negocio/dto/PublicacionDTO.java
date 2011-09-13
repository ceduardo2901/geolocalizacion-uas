package com.uas.gsiam.negocio.dto;

import java.io.Serializable;
import java.util.Date;

public class PublicacionDTO implements Serializable{

	private String idPublicacion;
	private String comentario;
	private Integer idUsuario;
	private Integer idSitio;
	private Date fecha;
	private Float puntaje;
	
	public Float getPuntaje() {
		return puntaje;
	}

	public void setPuntaje(Float puntaje) {
		this.puntaje = puntaje;
	}

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
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdSitio() {
		return idSitio;
	}
	public void setIdSitio(Integer idSitio) {
		this.idSitio = idSitio;
	}
	
	
	
}
