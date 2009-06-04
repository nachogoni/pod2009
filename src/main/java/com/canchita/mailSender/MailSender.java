package com.canchita.mailSender;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class MailSender {

	protected Logger logger;
	private static final String LOG4J_PROPERTIES = "log4j.properties";

	private List<String> to = null;
	private String subject = null;
	private StringBuffer body = null;
	// private String smtpServer = "smtp.fibertel.com.ar";
	// private String smtpServerPort = "25";
	private String smtpServer = "localhost";
	private String smtpServerPort = "2525";
//	private String smtpServer = "smtp.alu.itba.edu.ar";
//	private String smtpServerPort = "25";
	private String user = "canchita.noreply";
	private String from = "canchita.noreply@fibertel.com.ar";
	private String passwd = "mil33t";

	{
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				LOG4J_PROPERTIES);
		PropertyConfigurator.configure(url);
		this.logger = Logger.getLogger(MailSender.class.getName());
	}

	public MailSender(String from, String to, String subject, String body,
			String smtpServer, String smtpServerPort, String user, String passwd) {
		super();
		this.from = from;
		this.to = new ArrayList<String>();
		this.to.add(to);
		this.subject = subject;
		this.body = new StringBuffer(body);
		this.smtpServer = smtpServer;
		this.smtpServerPort = smtpServerPort;
		this.user = user;
		this.passwd = passwd;
	}

	public MailSender(String to, String subject, String body) {
		super();
		this.to = new ArrayList<String>();
		this.to.add(to);
		this.subject = subject;
		this.body = new StringBuffer(body);
	}
	
	public MailSender() {
		super();
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public void setTo(String to) {
		this.to = new ArrayList<String>();
		this.to.add(to);
	}

	public void addTo(String to) {
		this.to.add(to);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body.toString();
	}

	public void setBody(String body) {
		this.body = new StringBuffer(body);
	}

	public void appendBody(String body) {
		this.body.append(body);
	}

	public String getSmtpServer() {
		return smtpServer;
	}

	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	public String getSmtpServerPort() {
		return smtpServerPort;
	}

	public void setSmtpServerPort(String smtpServerPort) {
		this.smtpServerPort = smtpServerPort;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Boolean send() {

		if (this.to.isEmpty() || this.body == null || this.subject == null)
			return false;
		
		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", smtpServer);
			props.setProperty("mail.smtp.port", smtpServerPort);
			props.setProperty("mail.smtp.user", user);
			props.setProperty("mail.smtp.auth", "false");

			// Preparamos la sesion
			Session session = Session.getDefaultInstance(props);

			// Construimos el mensaje
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));

            for(String another : to)
            {
                message.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(another));
            }

            message.setSubject(subject);
			message.setText(body.toString());

			// Lo enviamos.
			Transport t = session.getTransport("smtp");
			t.connect(user, passwd);
			t.sendMessage(message, message.getAllRecipients());

			// Cierre.
			t.close();

			logger.info("Mail enviado correctamente para '" + to
					+ "', asunto: '" + subject + "', cuerpo: '" + body + "'.");
		} catch (Exception e) {
			logger.error("Error al enviar el mail para '" + to + "', asunto: '"
					+ subject + "', cuerpo: '" + body + "'.");
			e.printStackTrace();
		}

		return true;
	}

}
