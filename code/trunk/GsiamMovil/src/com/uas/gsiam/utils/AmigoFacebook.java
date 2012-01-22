package com.uas.gsiam.utils;

/**
 * Clase que determina un usuario de facebook en la aplicación para mostrar una
 * lista de amigos de facebook
 * 
 * @author Antonio
 * 
 */
public class AmigoFacebook {

	private String id;
	private String nombre;
	private String fotoUrl;
	private boolean seleccionado;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFotoUrl() {
		return fotoUrl;
	}

	public void setFotoUrl(String fotoUrl) {
		this.fotoUrl = fotoUrl;
	}

	public boolean isSeleccionado() {
		return seleccionado;
	}

	public void setSeleccionado(boolean seleccionado) {
		this.seleccionado = seleccionado;
	}

	public void seleccionar() {
		seleccionado = !seleccionado;
	}

}
