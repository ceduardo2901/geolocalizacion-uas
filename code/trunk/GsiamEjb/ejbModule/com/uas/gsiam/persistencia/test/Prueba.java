package com.uas.gsiam.persistencia.test;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.URLName;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.uas.gsiam.negocio.dto.UsuarioDTO;
import com.uas.gsiam.negocio.excepciones.RuntimeApplicationException;
import com.uas.gsiam.persistencia.dao.impl.UsuarioDAO;
import com.uas.gsiam.persistencia.utiles.Constantes;
import com.uas.gsiam.persistencia.utiles.email.EmailTemplate;
import com.uas.gsiam.persistencia.utiles.email.EmailTemplateFactory;
import com.uas.gsiam.persistencia.utiles.email.EmailSender;

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