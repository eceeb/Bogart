package de.eliyo.utils;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Stateless
public class MailSender {
	
	private static final Logger logger = Logger.getLogger( MailSender.class.getName() );
	
	private static final String SMTP_AUTH_USER = System.getenv("MANDRILL_SMTP_AUTH_USER");
	private static final String SMTP_AUTH_PWD = System.getenv("MANDRILL_SMTP_AUTH_PWD");
	private static final String SMTP_HOST_NAME = "smtp.mandrillapp.com";
	private static final String SMTP_PORT_NUM = "587";

	private String toAddress;
	private String subject;
	private String content;
	
	public void notifyAdmin(Exception x) {
		to("eliyo@me.com").withSubject("something went wrong").withContent(x.toString()).send();
	}
	
	public void notifySubsriber(String email, String seek, String url) {
		to(email).withSubject("found something").withContent("found " + seek + " on " + url).send();
	}
	
	private MailSender to(String toAddress) {
		this.toAddress = toAddress;
		return this;
	}
	
	private MailSender withSubject(String subject) {
		this.subject = subject;
		return this;
	}
	
	private MailSender withContent(String content) {
		this.content = content;
		return this;
	}
	
	private void send() {
		InternetAddress internetAddress = null;
		try {
			internetAddress = new InternetAddress(toAddress);
		} catch (AddressException e1) {
			e1.printStackTrace();
		}
		String from = "elcSearchEngine@paradise.com";
		Transport transport = null;

		Properties properties = System.getProperties();
		properties.put("mail.smtp.host", SMTP_HOST_NAME);
		properties.put("mail.smtp.user", SMTP_AUTH_USER);
		properties.put("mail.smtp.port", SMTP_PORT_NUM);
		properties.put("mail.smtp.socketFactory.port", SMTP_PORT_NUM);
		properties.put("mail.smtp.auth", "true");
		properties.put("javax.net.ssl.SSLSocketFactory", "false");

		Authenticator auth = new SMTPAuthenticator();
		Session session = Session.getDefaultInstance(properties, auth);

		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(from));
			message.setRecipient(Message.RecipientType.TO, internetAddress);
			message.setSubject(subject);
			message.setContent(content, "text/plain");

			transport = session.getTransport("smtp");
			transport.connect(SMTP_HOST_NAME, Integer.valueOf(SMTP_PORT_NUM).intValue(), SMTP_AUTH_USER, SMTP_AUTH_PWD);

			transport.sendMessage(message, message.getAllRecipients());
			logger.log(Level.INFO, "Message sent successfully");
		} catch (Exception x) {
			logger.log( Level.SEVERE, x.toString(), x );
		}
	}

	private class SMTPAuthenticator extends Authenticator {
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(SMTP_AUTH_USER, SMTP_AUTH_PWD);
		}
	}


}
