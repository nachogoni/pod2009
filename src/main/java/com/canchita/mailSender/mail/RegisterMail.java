package com.canchita.mailSender.mail;

import com.canchita.mailSender.MailSender;

public class RegisterMail {

	private static final String SUBJECT = "Canchita - Regitración Exitosa";
	private static final String URL = "#baseUrl/user/finishregister?hash=";
	private static final String BODY = "<html><head></head><body>"
			+ "Hola #name<br />¡Gracias por registrate en la"
			+ " canchita!<br /> Ahora necesitamos confirmar tu dirección de correo"
			+ " electrónico para finalizar el proceso de registración. Por"
			+ " favor haz click en el siguiente link para confirmar"
			+ " tu dirección de mail. <br />"
			+ " <a href=\"#link\" target=\"_blank\">¡Comenzar a usar mi canchita!</a> <br />"
			+ " Si esto no funciona copia y pega el"
			+ " siguiente link en tu navegador #link <br /> <br /> Muchas Gracias por"
			+ " ser parte de nuestra comunidad, <br /> <br /> El equipo de la Canchita"
			+ "<body></html>";

	public static void sendMail(String email, String name, String hash,
			String baseUrl) {

		String link = RegisterMail.URL.replaceFirst("#baseUrl", baseUrl) + hash;

		String body = BODY.replaceAll("#link", link)
				.replaceFirst("#name", name);

		MailSender mailSender = new MailSender(email, SUBJECT, body);

		mailSender.send();

	}

}
