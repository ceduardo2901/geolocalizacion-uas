package com.uas.gsiam.negocio.excepciones;

public class PublicacionExcepcion extends Exception{

	private static final long serialVersionUID = 1L;
	private String mensaje;

	public PublicacionExcepcion(String mensaje){
		super(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
