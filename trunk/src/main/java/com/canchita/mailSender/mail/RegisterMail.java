package com.canchita.mailSender.mail;

import java.io.IOException;

import com.canchita.mailSender.MailSender;
import com.canchita.mailSender.helper.MailHelper;

public class RegisterMail {

	private static final String SUBJECT = "Canchita - Regitración Exitosa";
	private static final String URL = "#baseUrl/user/finishregister?hash=";
	private static final String REGISTER_MAIL = "/register.mail";

	public static void sendMail(String email, String name, String hash,
			String baseUrl, String mailPath) throws IOException {

		String link = RegisterMail.URL.replaceFirst("#baseUrl", baseUrl) + hash;

		String body = MailHelper.getMail(mailPath + REGISTER_MAIL);
		
		body = body.replaceAll("#link", link)
				.replaceFirst("#name", name);

		MailSender mailSender = new MailSender(email, SUBJECT, body);

		mailSender.send();

	}

}
