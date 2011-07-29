package com.uas.gsiam.negocio.excepciones;

public class SitioYaExisteExcepcion extends Exception{

	
	private String mensaje;

	public SitioYaExisteExcepcion(String mensaje){
		super(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
