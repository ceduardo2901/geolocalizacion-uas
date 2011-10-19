package com.uas.gsiam.persistencia.utiles.email;

import java.util.Properties;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

import com.uas.gsiam.negocio.excepciones.RuntimeApplicationException;


public final class EmailTemplateFactory {

	private VelocityEngine engine;

	private static final EmailTemplateFactory instance = new EmailTemplateFactory();

	private EmailTemplateFactory() {
		try {

			this.engine = new VelocityEngine();
			/*	Properties props = new Properties();
				props.setProperty(VelocityEngine.RESOURCE_LOADER, "classpath");
			props.setProperty("classpath." + VelocityEngine.RESOURCE_LOADER
					+ ".class", ClasspathResourceLoader.class.getName());
			engine.init(props);
			*/
		//	props.setProperty("file.resource.loader.path", "/mailTemplates");

			engine.init();
			
		} catch (Exception e) {
			throw new RuntimeApplicationException(
					"EmailTemplateFactory Initialization Error !!", e);
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
		return new EmailTemplate(instance.engine.getTemplate(template));
	}

}
