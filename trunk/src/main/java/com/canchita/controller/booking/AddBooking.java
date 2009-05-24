package com.canchita.controller.booking;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeParser;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;

/**
 * Servlet implementation class AddBooking
 */
public class AddBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets the form parameters for the view
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//Obtener los horarios de la cancha y pasarselos a la vista
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * Gets parameters from form and if everything is okay, it tries
	 * to add the booking
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Long id = null;
		DateTime date = null;

		try {
			id = Long.parseLong(request.getParameter("id"));
		}
		catch( NumberFormatException nfe ) {
			//acumular error
		}

		String dateParameter = request.getParameter("date");
		
		DateTimeFormatter parser =
		    DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			date = parser.parseDateTime(dateParameter);
		} catch (ParseException e) {
			// agregar error
		}

			
		String from = request.getParameter("from");
		String to = request.getParameter("to");

		DateTime startTime = this.getDate(date,from);
		DateTime endTime = this.getDate(date,to);
		
		BookingServiceProtocol bookingService = new BookingService();
		
		bookingService.saveBooking(id, startTime, endTime);
		
		//Levantar los aprametros del formulario
		//Llamar a saveBooking

		
		
	}

	private DateTime getDate(DateTime date, String offset) {
		//return date.withTime(int hourOfDay, int minuteOfHour, 0, 0);
		return null;
	}

}
