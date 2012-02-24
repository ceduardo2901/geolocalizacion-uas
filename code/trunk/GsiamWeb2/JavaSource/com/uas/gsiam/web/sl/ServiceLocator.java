package com.uas.gsiam.web.sl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.servicios.impl.SitioServicioBean;

/**
 * Clase que representa el patron Service Locator
 * 
 */
public class ServiceLocator {

	private static final Logger logger = LoggerFactory
			.getLogger(SitioServicioBean.class);
	
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
			logger.debug("Obtener el ejb con direccion: "+beanAddress);
		} catch (NamingException ne) {
			logger.error(ne.getMessage(), ne);
			throw ne;
		}
		return obj;

	}

}
