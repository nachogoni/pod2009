package com.canchita.controller.rss;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.mailSender.MailSender;

/**
 * Servlet implementation class DeadBooking
 */
public class DeadBooking extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeadBooking() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		MailSender mail = new MailSender("nachogoni@gmail.com",
				"Alguien quiere saber las reservas caidas y no esta hecho!",
				"Estaria bueno hacer el rss de DeadBooking.");

		mail.addTo("macarse@gmail.com");
		mail.addTo("pabloks@gmail.com");
		mail.addTo("cabral.martin@gmail.com");
		mail.addTo("mvidal@gmail.com");
		mail.addTo("martin.palombo@gmail.com");

		mail.appendBody("\n Para usar el envio de mail, tienen que hacer un tunnel:");
		mail.appendBody("ssh -L2525:smtp.fibertel.com.ar:25 pampero");
		mail.appendBody("(o pampero.itba.edu.ar depende donde esten)\n");
		
		mail.send();

	}

}
