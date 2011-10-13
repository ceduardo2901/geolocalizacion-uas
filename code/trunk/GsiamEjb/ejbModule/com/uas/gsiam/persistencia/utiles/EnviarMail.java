package com.uas.gsiam.persistencia.utiles;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EnviarMail implements Runnable{
	
	//TODO Poner esto en un archivo...
	private static final String EMAIL_GSIAM = "gsiam.notificacion@gmail.com";
	private static final String PASS_GSIAM = "gsiam.2011";
	private static final String HOST = "smtp.gmail.com";
	private static final String PORT = "587";
	
	private String emailDestinatario;
	private String asunto;
	private String cuerpo;
	
	
	public EnviarMail(String emailDestinatario,String asunto, String cuerpo) {
		
		this.emailDestinatario = emailDestinatario;
		this.asunto = asunto;
		this.cuerpo = cuerpo;
		
	}



	@Override
	public void run() {
		
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", HOST);
			props.setProperty("mail.smtp.port", PORT);
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

			//TODO QUe hacemos con estas excepciones?
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/*  
	
	public void enviarMailConAdjunto(String emailDestinatario,String asunto,String cuerpo, String rutaArchivo, String nombreArchivo) throws MessagingException{
		
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
	*/
}
	

