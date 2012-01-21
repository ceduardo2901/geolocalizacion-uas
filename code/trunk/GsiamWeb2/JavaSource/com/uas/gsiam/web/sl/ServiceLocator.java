package com.uas.gsiam.web.sl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Clase que representa el patron Service Locator
 * 
 */
public class ServiceLocator {

	private ServiceLocator() throws NamingException {
	}

	private static Context getInitialContext() throws NamingException {

		return new InitialContext();
	}

	/**
	 * Metodo que obtiene el ejb publicado con una determinada uri
	 * 
	 * @param beanAddress
	 * @return el objeto que se encuentra publicado en la direccion ingresada por parametro
	 * @throws NamingException
	 */
	public static Object getBean(String beanAddress) throws NamingException {
		final Context context;
		Object obj = new Object();
		try {
			context = getInitialContext();
			obj = context.lookup(beanAddress);
		} catch (NamingException ne) {
			throw ne;
		}
		return obj;

	}

}
