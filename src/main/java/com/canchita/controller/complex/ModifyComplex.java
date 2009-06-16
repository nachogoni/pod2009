package com.canchita.controller.complex;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class ModifyComplex
 */
public class ModifyComplex extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyComplex() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		ComplexService service = new ComplexService();
		Long id = null;

		/* Get Form */
		FormHandler formulario = new FormAddComplex();

		ErrorManager error = new ErrorManager();

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			error.add("El identificador debe ser un número");
			logger.error("Error leyendo id de complejo");
		}

		logger.debug("Buscando información del complejo " + id);
		try {
			formulario = new FormAddComplex(service.getById(id), service
					.getDefaultExpirationPolicy(id));
		} catch (PersistenceException e) {
			error.add(e);
			logger.error("Error buscando información del complejo " + id + " " + e.getMessage());
		}

		if (error.size() != 0) {
			request.setAttribute("formulario", formulario);

			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			return;
		}

		/* Form is sent to the view */
		request.setAttribute("formulario", formulario);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		logger.debug("Saliendo del controlador");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ComplexService modifyService = new ComplexService();

		logger.debug("POST request");
		FormHandler formulario = new FormAddComplex();

		/* Load form with request values */
		formulario.loadValues(request);

		if (!formulario.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		ErrorManager error = new ErrorManager();

		String name = request.getParameter("name");
		String description = request.getParameter("description");

		String zipCode = request.getParameter("zipcode");
		String town = request.getParameter("town");
		String state = request.getParameter("state");
		String neighbourhood = request.getParameter("neighbourhood");
		String country = request.getParameter("country");
		String address = request.getParameter("address");
		// TODO: ver cómo poner sólo los que faltan en la db.
		String telephones[] = request.getParameterValues("telephone");

		if (name == null) {
			error.add("Falta el nombre del Complejo");
		}
		if (address == null) {
			error.add("Falta la dirección del complejo");
		}
		if (town == null) {
			error.add("Falta la cuidad donde se encuentra el complejo");
		}
		if (state == null) {
			error
					.add("Falta la provincia/estado donde se encuentra el complejo");
		}

		Long id = null;
		Integer depositLimit = null;
		Integer bookingLimit = null;

		try {

			id = Long.parseLong(request.getParameter("id"));
			depositLimit = Integer.parseInt(request
					.getParameter("depositLimit"));
			bookingLimit = Integer.parseInt(request
					.getParameter("bookingLimit"));
		} catch (NumberFormatException nfe) {
			error.add("Valores para el sistema de reservas incorrectos");
		}

		if (error.size() != 0) {
			logger.debug("Error en el formulario");
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		}

		ArrayList<DateTime> schedule = null;
		String[] daysOfWeek = { "fecha_lunes_inicio", "fecha_lunes_fin",
				"fecha_martes_inicio", "fecha_martes_fin",
				"fecha_miercoles_inicio", "fecha_miercoles_fin",
				"fecha_jueves_inicio", "fecha_jueves_fin",
				"fecha_viernes_inicio", "fecha_viernes_fin",
				"fecha_sabado_inicio", "fecha_sabado_fin",
				"fecha_domingo_inicio", "fecha_domingo_fin" };

		try {

			schedule = new ArrayList<DateTime>();

			DateTimeFormatter parser = DateTimeFormat.forPattern("HH:mm");
			String aDay = null;

			// TODO: Refactor esto!
			for (int i = 0; i < daysOfWeek.length; i++) {

				aDay = request.getParameter(daysOfWeek[i]);
				schedule.add(parser.parseDateTime(aDay));
				aDay = request.getParameter(daysOfWeek[++i]);
				schedule.add(parser.parseDateTime(aDay));
			}

			// TODO Atrapar la excepcion posta (o crearla si no hay nada
			// adecuado)
		} catch (NullPointerException e) {
			error.add("Faltan valores para los horarios");
		} catch (Exception e) {
			error.add("Valores para los horarios incorrectos");
		}
		try {
			//Update Basic Info
			modifyService.updateComplex(id, name, description, address,
					zipCode, neighbourhood, town, state, country);

			//Update expiration policy
			modifyService.setDefaultExpiration(id, bookingLimit, depositLimit);
			
			//Update telephones
			modifyService.addTelephones(id, telephones);
			
			//Update timetable
			modifyService.addTimeTable(id, schedule.get(0), schedule.get(1),
					schedule.get(2), schedule.get(3), schedule.get(4), schedule
							.get(5), schedule.get(6), schedule.get(7), schedule
							.get(8), schedule.get(9), schedule.get(10),
					schedule.get(11), schedule.get(12), schedule.get(13));

			// TODO loguear los errores
		} catch (ElementExistsException ee) {
			logger.error("Error modificando el complejo con id " + id);
			error.add(ee);

		} catch (PersistenceException e) {
			logger.error("Error modificando el complejo con id " + id);
			error.add(e);

		} catch (IllegalArgumentException e) {
			logger.error("Error modificando el complejo con id " + id);
			error.add(e);

		} catch (InvalidScheduleException e) {
			logger.error("Error modificando el complejo con id " + id);
			error.add(e);

		} catch (Exception e) {
			logger.error("Error modificando el complejo con id " + id);
			error.add(e);

		}

		if (error.size() != 0) {
			logger.error("Error modificando el complejo con id " + id);
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		}

		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);

	}

	private void failure(HttpServletRequest request,
			HttpServletResponse response, ErrorManager error)
			throws ServletException, IOException {
		request.setAttribute("errorManager", error);

		UrlMapper.getInstance().forwardFailure(this, request, response,
				UrlMapperType.POST);

	}

}
