package com.uas.gsiam.negocio.excepciones;

public class SitioYaExisteExcepcion extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
