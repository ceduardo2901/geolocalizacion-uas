package com.uas.gsiam.persistencia.test;

import java.util.ArrayList;

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

			ArrayList<String> lista = new ArrayList<String>();
			lista.add("mloure@gmail.com");
			lista.add("peludens@hotmail.com");
			
			mail.setListEmailDestinatario(lista);
			mail.setSubject("esta es una prueba lalala");
			

			new Thread(mail).start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
		
		
		
		
		
	}
}