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
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


@Stateless
public class MailSender {
	
	
	private static final String SMTP_AUTH_USER = System.getenv("MANDRILL_SMTP_AUTH_USER");
	private static final String SMTP_AUTH_PWD  = System.getenv("MANDRILL_SMTP_AUTH_PWD");
	private static final String SMTP_HOST_NAME = "smtp.mandrillapp.com";
	private static final String SMTP_PORT_NUM  = "587";
	private static final String ADMIN_MAIL_ADDRESS = System.getenv("ADMIN_MAIL_ADDRESS");

	private static final Logger logger = Logger.getLogger( MailSender.class.getName() );
	
	public void notifyAdmin(Exception x) {
		send(ADMIN_MAIL_ADDRESS, "somethind went wrong", x.toString());
	}
	
	public void notifySubsriber(String email, String seek, String url) {
		String content = "Hello, I found '" + seek + "' on: " + url;
		send(email, "Found something", content);
	}
	
	private void send(String toAddress, String subject, String content) {
		try {
			InternetAddress internetAddress = null;
			internetAddress = new InternetAddress(toAddress);

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
			message.setFrom(new InternetAddress("Service@search-that-site.com"));
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
