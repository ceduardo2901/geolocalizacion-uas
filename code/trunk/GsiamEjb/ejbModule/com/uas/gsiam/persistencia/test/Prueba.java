package com.uas.gsiam.persistencia.test;

import com.uas.gsiam.persistencia.utiles.email.EmailSender;
import com.uas.gsiam.persistencia.utiles.email.EmailTemplate;
import com.uas.gsiam.persistencia.utiles.email.EmailTemplateFactory;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	
		EmailTemplate template;
		try {
			template = EmailTemplateFactory.createEmailTemplate("mailTemplates/solicitud.vm");
			template.put("p_nombre_aprobador", "carlitos");
			template.put("p_nombre_solicitante", "josesito");

			EmailSender mail = new EmailSender();
			mail.setTemplate(template);
			
			mail.setEmailDestinatario("mloure@gmail.com");
			mail.setSubject("esta es una prueba lalala");
			

			new Thread(mail).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
		
		
		
		
	}
}