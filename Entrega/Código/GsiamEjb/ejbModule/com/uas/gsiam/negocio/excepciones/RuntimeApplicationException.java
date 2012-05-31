package com.uas.gsiam.negocio.excepciones;

public class RuntimeApplicationException extends RuntimeException {


	private static final long serialVersionUID = 1L;

	public RuntimeApplicationException(Throwable cause) {
        super(cause);
    }

    public RuntimeApplicationException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }

    public RuntimeApplicationException(String mensaje) {
        super(mensaje);
    }
}
