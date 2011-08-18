package com.uas.gsiam.persistencia.utiles;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;


/**
 * @author JAAC
 *
 */
public class MailUtil {
	
	//TODO Poner esto en un archivo...
	private static String EMAIL_GSIAM = "gsiam.notificacion@gmail.com";
	private static String PASS_GSIAM = "gsiam.2011";
	
	public static void enviarMail(String emailDestinatario,String asunto,String cuerpo) throws AddressException, MessagingException{
		 
		 
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port","587");
		props.setProperty("mail.smtp.user", EMAIL_GSIAM);
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
		     
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress(EMAIL_GSIAM));
			
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailDestinatario));
			
		message.setSubject(asunto);
		message.setText(cuerpo);
		
		Transport t = session.getTransport("smtp");
		t.connect(EMAIL_GSIAM, PASS_GSIAM);
		t.sendMessage(message,message.getAllRecipients());
		t.close();

		 
	 }
		    
	
	public static void enviarMailConAdjunto(String emailDestinatario,String asunto,String cuerpo, String rutaArchivo, String nombreArchivo) throws MessagingException{
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port","587");
		props.setProperty("mail.smtp.user", EMAIL_GSIAM);
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, null);
			
		BodyPart texto = new MimeBodyPart();
		texto.setText(cuerpo);
		
		BodyPart adjunto = new MimeBodyPart();
		adjunto.setDataHandler(new DataHandler(new FileDataSource(rutaArchivo)));
		adjunto.setFileName(nombreArchivo);

		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		multiParte.addBodyPart(adjunto);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailDestinatario));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(EMAIL_GSIAM));
		message.setSubject(asunto);
		message.setContent(multiParte);
		
		Transport t = session.getTransport("smtp");
		t.connect(EMAIL_GSIAM, PASS_GSIAM);
		t.sendMessage(message,message.getAllRecipients());
		t.close();

		
	}
	
}
	

