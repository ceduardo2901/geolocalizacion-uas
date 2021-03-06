package com.uas.gsiam.negocio.dto;

import java.io.Serializable;
import java.sql.Timestamp;

public class PosicionUsuarioDTO implements Serializable{


	private static final long serialVersionUID = 1L;
	private int idUsuario;
	private Double lon;
	private Double lat;
	private Timestamp fechaActualizacion;
	
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
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
	public Timestamp getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Timestamp fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	
	
	
	
}
