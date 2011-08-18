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
import com.uas.gsiam.persistencia.dao.impl.UsuarioDAO;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
/*
		System.out.println("#### Arranca la cosa.....");
		
		UsuarioDTO userDTO = new UsuarioDTO();
		userDTO.setEmail("pepe@gmail.com");
		userDTO.setNombre("Pedro Petero");
		userDTO.setPassword("pass");
		UsuarioDAO dao = new UsuarioDAO();
		
		
// Login...
		
/*		UsuarioDTO Utest;
		try {
			Utest = dao.login(userDTO);
			System.out.println("user id: " + Utest.getId());
			System.out.println("user nombre: " + Utest.getNombre());
			System.out.println("user fecha: " + Utest.getFechaNacimiento());
		} catch (UsuarioNoExisteExcepcion e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
*/
		

		
		
		
// Exist...
		/*
		if (dao.existeUsuario(userDTO))
			System.out.println("existe");
		else
			System.out.println("no existe");
		
		System.out.println("#### Termina la cosa.....");
		*/
		
		
	//////////////////////////////////

		
		try {
	/*	
		Properties props = new Properties();
		props.setProperty("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.port","587");
		props.setProperty("mail.smtp.user", "gsiam.notificacion@gmail.com");
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.smtp.starttls.enable", "true");
	     
		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		
		message.setFrom(new InternetAddress("gsiam.notificacion@gmail.com"));
		
		message.addRecipient(Message.RecipientType.TO, new InternetAddress("mloure@gmail.com"));
		
		message.setSubject("Hola");
		message.setText("Mensajito con Java Mail<br>" + "<b>de</b> los <i>buenos</i>." + "poque si", "ISO-8859-1","html");

		Transport t = session.getTransport("smtp");
		t.connect("gsiam.notificacion@gmail.com","gsiam.2011");
		t.sendMessage(message,message.getAllRecipients());
		t.close();
	    
		*/
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.setProperty("mail.smtp.starttls.enable", "true");
		props.setProperty("mail.smtp.port","587");
		props.setProperty("mail.smtp.user", "gsiam.notificacion@gmail.com");
		props.setProperty("mail.smtp.auth", "true");

		Session session = Session.getDefaultInstance(props, null);
			
		BodyPart texto = new MimeBodyPart();
		texto.setText("cuerpo");
		
		BodyPart adjunto = new MimeBodyPart();
		adjunto.setDataHandler(new DataHandler(new FileDataSource("E:/futbol.PNG")));
		adjunto.setFileName("nombre del Archivo");

		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		multiParte.addBodyPart(adjunto);

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress("gsiam.notificacion@gmail.com"));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress("mloure@gmail.com"));
		message.setSubject("asunto");
		message.setContent(multiParte);
		
		Transport t = session.getTransport("smtp");
		t.connect("gsiam.notificacion@gmail.com", "gsiam.2011");
		t.sendMessage(message,message.getAllRecipients());
		t.close();
		
		
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//////////////////////////////////

	}

}
