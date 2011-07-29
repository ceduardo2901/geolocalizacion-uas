package com.uas.gsiam.negocio.excepciones;

public class SitioNoExisteExcepcion extends Exception{

	
	private String mensaje;

	public SitioNoExisteExcepcion(String mensaje){
		super(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}
