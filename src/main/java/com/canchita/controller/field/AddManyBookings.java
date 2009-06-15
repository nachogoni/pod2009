package com.canchita.controller.field;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.UserException;
import com.canchita.model.field.Field;
import com.canchita.model.user.CommonUser;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;

/**
 * Servlet implementation class AddBooking
 */
public class AddManyBookings extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Sets the form parameters for the view
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ErrorManager error = new ErrorManager();
		FieldServiceProtocol fieldService = new FieldService();

		String stringId = request.getParameter("id");

		Long id;

		try {
			id = Long.parseLong(stringId);
		} catch (NumberFormatException nfe) {
			error.add("El identificador de la cancha debe ser un número");
			this.failureGET(request, response, error);
			return;
		}
		
		Field field;
		try {
			field = fieldService.getById(id);
		} catch (PersistenceException e) {
			error.add(e);
			this.failureGET(request, response, error);
			return;
		}

		request.setAttribute("field", field);
		
		logger.debug("GET request");

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * Gets parameters from form and if everything is okay, it tries to add the
	 * booking
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("POST request");

		/*
		 * TODO falta agregar el manejo de 23:00 00:00 igual creo que el jugo es
		 * poner que atiende de 23:00 a 00:00 siendo 00:00 el mismo dia en el
		 * horario de la cancha (o quizas no) VERLO BIEN
		 */

		Long id = null;
		DateTime from = null,to = null;
		DateTime startTimeFrom, endTimeFrom,startTimeTo,endTimeTo;

		String idParameter = request.getParameter("id");
		String fromParameter = request.getParameter("from");
		String toParameter = request.getParameter("to");
		String whenParameter = request.getParameter("when");

		ErrorManager error = new ErrorManager();

		if (idParameter == null) {
			error.add("Falta el identificador de cancha");
		}
		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			error.add("El identificador de cancha debe ser un número");
		}
		
		Field field;
		FieldServiceProtocol fieldService = new FieldService();
		try {
			field = fieldService.getById(id);
		} catch (PersistenceException e) {
			error.add(e);
			this.failurePOST(request, response, error);
			return;
		}

		request.setAttribute("field", field);

		if (fromParameter == null || fromParameter.equals("")) {
			error.add("Falta la fecha de reserva");
		}

		if (whenParameter == null) {
			error.add("Falta el período en el cual se reservará la cancha");
		}

		if (error.size() != 0) {
			logger.debug("Error en el formulario");
			this.failurePOST(request, response, error);
			return;
		}

		DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			from = parser.parseDateTime(fromParameter);
		} catch (Exception e) {
			error
					.add("La fecha desde está en un formato inválido, debe ser de la forma \"dd/mm/yyyy\"");
		}

		try {
			to = parser.parseDateTime(toParameter);
		} catch (Exception e) {
			error
					.add("La fecha hasta está en un formato inválido, debe ser de la forma \"dd/mm/yyyy\"");
		}

		
		if (error.size() != 0) {
			logger.debug("Error en el formulario");
			this.failurePOST(request, response, error);
			return;
		}

		String[] when = whenParameter.split(" ");
		try {
			startTimeFrom = this.getDate(from, when[0]);
			endTimeFrom = this.getDate(from, when[1]);
			
			startTimeTo = this.getDate(to, when[0]);
			endTimeTo = this.getDate(to, when[1]);
			
			if( when[1].equals("00:00") ) {
				endTimeFrom = endTimeFrom.plusDays(1);
				endTimeTo = endTimeTo.plusDays(1);
			}
			
		} catch (Exception e) {
			error
					.add("El horario de reserva está malformado, debe ser de la forma \"hh:mm hh:mm\"");
			logger.debug("Error en el formulario, horario inválido");
			this.failurePOST(request, response, error);
			return;
		}

		BookingServiceProtocol bookingService = new BookingService();
		CommonUser user = (CommonUser) request.getSession().getAttribute("user");
		List<Booking> bookings = null;
		try {
			bookings = bookingService.saveManyBookings(user,id, startTimeFrom, endTimeFrom,startTimeTo,endTimeTo);
		} catch (ElementExistsException e) {
			error.add(e);
		} catch (ElementNotExistsException ene) {
			error.add(ene);
		} catch (PersistenceException pe) {
			error.add("Error en el servidor, por favor intente nuevamente");
		} catch (BookingException e) {
			error.add(e);
		} catch (UserException e) {
			error.add(e);
		}

		if (error.size() != 0) {
			logger.debug("Error en el formulario");
			this.failurePOST(request, response, error);
			return;
		}

		request.setAttribute("success", true);
		request.setAttribute("bookings", bookings);
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.POST);

	}

	private DateTime getDate(DateTime date, String offset) {

		String[] hoursAndMins = offset.split(":");

		return date.withTime(Integer.parseInt(hoursAndMins[0]), Integer
				.parseInt(hoursAndMins[1]), 0, 0);

	}

}
