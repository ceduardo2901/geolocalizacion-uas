package com.uas.gsiam.test.dto;


import java.io.Serializable;


public class UsuarioDTO implements Serializable{

	// TODO: Como almacenamos la foto del usuario??

	
	private static final long serialVersionUID = 1L;
	private int id;
	private String nombre;
	private String email;
	private String password;
	private byte [] avatar;
	private boolean solicitudEnviada;
	private boolean solicitudRecibida;
	private PosicionUsuarioDTO posicion;
	
	public PosicionUsuarioDTO getPosicion() {
		return posicion;
	}

	public void setPosicion(PosicionUsuarioDTO posicion) {
		this.posicion = posicion;
	}

	public boolean isSolicitudRecibida() {
		return solicitudRecibida;
	}

	public void setSolicitudRecibida(boolean solicitudRecibida) {
		this.solicitudRecibida = solicitudRecibida;
	}

	public boolean isSolicitudEnviada() {
		return solicitudEnviada;
	}

	public void setSolicitudEnviada(boolean solicitudEnviada) {
		this.solicitudEnviada = solicitudEnviada;
	}

	public byte[] getAvatar() {
		return avatar;
	}

	public void setAvatar(byte[] avatar) {
		this.avatar = avatar;
	}

	public UsuarioDTO() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
