package com.uas.gsiam.negocio.excepciones;

public class UsuarioExcepcion extends Exception{

	

	private static final long serialVersionUID = 1L;
	private String mensaje;

	public UsuarioExcepcion(String mensaje){
		super(mensaje);
		this.mensaje = mensaje;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
}
