package com.uas.gsiam.negocio.dto;

import java.io.Serializable;
import java.sql.Date;

public class SolicitudContactoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private int idUsuarioSolicitante;
	private int idUsuarioAprobador;
	private Date fechaSolicitud;
	private Date fechaAprobacion;
	
	
	public int getIdUsuarioSolicitante() {
		return idUsuarioSolicitante;
	}
	public void setIdUsuarioSolicitante(int idUsuarioSolicitante) {
		this.idUsuarioSolicitante = idUsuarioSolicitante;
	}
	public int getIdUsuarioAprobador() {
		return idUsuarioAprobador;
	}
	public void setIdUsuarioAprobador(int idUsuarioAprobador) {
		this.idUsuarioAprobador = idUsuarioAprobador;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	public Date getFechaAprobacion() {
		return fechaAprobacion;
	}
	public void setFechaAprobacion(Date fechaAprobacion) {
		this.fechaAprobacion = fechaAprobacion;
	}
	 
	
}
