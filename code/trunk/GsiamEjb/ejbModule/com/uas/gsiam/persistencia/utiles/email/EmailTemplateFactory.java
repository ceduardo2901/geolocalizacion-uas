package com.uas.gsiam.persistencia.utiles.email;

import org.apache.velocity.app.VelocityEngine;

import com.uas.gsiam.negocio.excepciones.RuntimeApplicationException;


public final class EmailTemplateFactory {

	private VelocityEngine engine;

	private static final EmailTemplateFactory instance = new EmailTemplateFactory();

	private EmailTemplateFactory() {
		try {

			this.engine = new VelocityEngine();
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
