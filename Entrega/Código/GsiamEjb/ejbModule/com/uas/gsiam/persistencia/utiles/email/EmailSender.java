package com.uas.gsiam.persistencia.utiles.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.uas.gsiam.negocio.servicios.impl.SitioServicioBean;

public class EmailSender implements Runnable {

	private static final Logger logger = LoggerFactory
			.getLogger(SitioServicioBean.class);

	private static final String EMAIL_GSIAM = "gsiam.notificacion@gmail.com";
	private static final String PASS_GSIAM = "gsiam.2011";
	private static final String HOST = "smtp.gmail.com";
	private static final String PORT = "587";

	private EmailTemplate template;

	private String emailDestinatario;

	private String subject;

	public EmailSender() {

	}

	public EmailTemplate getTemplate() {
		return template;
	}

	public void setTemplate(EmailTemplate template) {
		this.template = template;
	}

	public String getEmailDestinatario() {
		return emailDestinatario;
	}

	public void setEmailDestinatario(String emailDestinatario) {
		this.emailDestinatario = emailDestinatario;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public void run() {
		logger.info("Envio de mail");
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

			message.addRecipient(Message.RecipientType.BCC,
					new InternetAddress(emailDestinatario));

			message.setSubject(subject);

			MimeBodyPart mbp = new MimeBodyPart();
			mbp.setContent(template.toString(), "text/html");
			MimeMultipart multipart = new MimeMultipart();
			multipart.addBodyPart(mbp);

			message.setContent(multipart);

			Transport t = session.getTransport("smtp");
			t.connect(EMAIL_GSIAM, PASS_GSIAM);
			t.sendMessage(message, message.getAllRecipients());
			t.close();

		} catch (AddressException e) {
			logger.error(e.getMessage(), e);
		} catch (MessagingException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

	}

}
