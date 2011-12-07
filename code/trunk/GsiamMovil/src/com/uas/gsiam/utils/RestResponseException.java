package com.uas.gsiam.utils;

import org.springframework.http.ResponseEntity;

public class RestResponseException extends java.lang.RuntimeException {
	protected ResponseEntity<?> responseEntity;
	protected String mensaje;
	
	public RestResponseException () {
		super();
	}
	
	public RestResponseException (String msg) {
		super(msg);
		this.mensaje=msg;
	}
	
	public ResponseEntity<?> getResponseEntity () {
		return this.responseEntity;
	}

	public void setResponseEntity(ResponseEntity<?> responseEntity) {
		this.responseEntity = (ResponseEntity<?>) responseEntity;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
