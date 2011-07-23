package com.uas.gsiam.negocio;

public class Punto {

	private Double longitud;
	private Double latitud;

	public Punto() {

	}

	public Punto(Double lon, Double lat) {
		this.longitud = lon;
		this.latitud = lat;
	}

	public Double getLongitud() {
		return longitud;
	}

	public void setLongitud(Double longitud) {
		this.longitud = longitud;
	}

	public Double getLatitud() {
		return latitud;
	}

	public void setLatitud(Double latitud) {
		this.latitud = latitud;
	}

}
