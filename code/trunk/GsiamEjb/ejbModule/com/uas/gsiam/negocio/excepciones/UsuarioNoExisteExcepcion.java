package com.uas.gsiam.negocio.excepciones;

public class UsuarioNoExisteExcepcion extends Exception{

	
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
