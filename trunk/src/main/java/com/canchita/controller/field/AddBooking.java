package com.canchita.controller.field;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
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

		// Obtener los horarios de la cancha y pasarselos a la vista

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * Gets parameters from form and if everything is okay, it tries to add the
	 * booking
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Long id = null;
		DateTime date = null;
		
		String idParameter = request.getParameter("id");
		String dateParameter = request.getParameter("date");
		String whenParameter = request.getParameter("when");

		ErrorManager error = new ErrorManager();
		
		if (idParameter == null ) {
			error.add("Falta el identificador de cancha");
		}
		
		if( dateParameter == null || dateParameter.equals("") ) {
			error.add("Falta la fecha de reserva");
		}
		
		if( whenParameter == null) {
			error.add("Falta el período en el cual se reservará la cancha");
		}
		
		if( error.size() != 0 ) {
			this.failure(request,response,error);
			return;
		}
		
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			error.add(nfe);
		}

		DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			date = parser.parseDateTime(dateParameter);
		} catch (IllegalArgumentException e) {
			error.add(e);
		}

		if( error.size() != 0 ) {
			this.failure(request,response,error);
			return;
		}
		
		String[] when = whenParameter.split(" ");
		DateTime startTime = this.getDate(date, when[0]);
		DateTime endTime = this.getDate(date, when[1]);

		BookingServiceProtocol bookingService = new BookingService();

		try {
			bookingService.saveBooking(id, startTime, endTime);
		} catch (ElementExistsException e) {
			error.add(e);
		} catch (ElementNotExistsException ene) {
			error.add(ene);
		} catch (PersistenceException pe) {
			error.add(pe);
		}

		if( error.size() != 0 ) {
			this.failure(request,response,error);
			return;
		}
		
		//TODO redireccionar a la vista de reservas del usuario
	}

	private void failure(HttpServletRequest request, HttpServletResponse response, ErrorManager error) throws ServletException, IOException {
		request.setAttribute("errorManager", error);
		
		UrlMapper.getInstance().forwardFailure(this, request, response,
				UrlMapperType.GET);
		
	}

	private DateTime getDate(DateTime date, String offset) {

		String[] hoursAndMins = offset.split(":");

		return date.withTime(Integer.parseInt(hoursAndMins[0]), Integer
				.parseInt(hoursAndMins[1]), 0, 0);

	}

}
