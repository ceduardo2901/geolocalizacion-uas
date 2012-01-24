package com.uas.gsiam.persistencia.utiles.email;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

/**
 * Clase que obtiene el template para enviar los email de invitacion al sistema
 * 
 * @author Martín
 * 
 */
public class EmailTemplate {

	private VelocityContext context;
	private Template template;

	EmailTemplate(Template _template) {
		this.template = _template;
		this.context = new VelocityContext();
	}

	public void put(String key, Object value) {
		this.context.put(key, value);
	}

	public String toString() {
		StringWriter writer = new StringWriter();
		try {
			template.merge(context, writer);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return writer.toString();
	}

}
