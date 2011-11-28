package com.uas.gsiam.utils;

import org.springframework.http.ResponseEntity;

public class RestResponseException extends java.lang.RuntimeException {
	protected ResponseEntity<?> responseEntity;
	
	public RestResponseException () {
		super();
	}
	
	public ResponseEntity<?> getResponseEntity () {
		return this.responseEntity;
	}

	public void setResponseEntity(ResponseEntity<?> responseEntity) {
		this.responseEntity = (ResponseEntity<?>) responseEntity;
	}

}
