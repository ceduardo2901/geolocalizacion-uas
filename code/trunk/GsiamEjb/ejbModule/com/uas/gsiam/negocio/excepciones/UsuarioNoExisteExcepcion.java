package com.uas.gsiam.negocio.excepciones;

public class UsuarioNoExisteExcepcion extends Exception{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mensaje;

	public UsuarioNoExisteExcepcion(String mensaje){
		super(mensaje);
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
