package com.uas.gsiam.persistencia.utiles.email;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.excepciones.RuntimeApplicationException;
import com.uas.gsiam.negocio.servicios.impl.SitioServicioBean;

/**
 * 
 * Factory para la creacion de los template de emails
 * 
 * @author Martín
 * 
 */
public final class EmailTemplateFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(SitioServicioBean.class);
	
	private VelocityEngine engine;

	private static final EmailTemplateFactory instance = new EmailTemplateFactory();

	private EmailTemplateFactory() {
		try {

			this.engine = new VelocityEngine();

			Properties props = new Properties();
			props.setProperty("resource.loader", "class");
			props.setProperty("class.resource.loader.class",
					"org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

			engine.init(props);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeApplicationException(
					"EmailTemplateFactory error en la inicializacion", e);
		}
	}

	/**
	 * Create Email Template Instance
	 * 
	 * @param template
	 * @return
	 * @throws Exception
	 */
	public static EmailTemplate createEmailTemplate(String template)
			throws Exception {
		logger.debug("crear email template");
		return new EmailTemplate(instance.engine.getTemplate(template));
	}

}
