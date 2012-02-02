package com.uas.gsiam.test.dto;

import java.io.Serializable;
import java.util.Date;

public class PublicacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Integer idPublicacion;
	private String comentario;
	private Integer idUsuario;
	private String nombreUsuario;
	private Integer idSitio;
	private Date fecha;
	private Float puntaje;
	private byte [] foto;
	
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

	public Integer getIdPublicacion() {
		return idPublicacion;
	}
	public void setIdPublicacion(Integer idPublicacion) {
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

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}
	
	
	
}
