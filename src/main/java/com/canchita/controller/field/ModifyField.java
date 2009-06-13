package com.canchita.controller.field;

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
import com.canchita.model.field.FloorType;
import com.canchita.service.FieldService;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class ModifyField
 */
public class ModifyField extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyField() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		FieldService service = new FieldService();
		Long id = null;
		FormHandler formulario = new FormField();

		ErrorManager error = new ErrorManager();

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			error.add("El identificador debe ser un número");
			logger.error("Error leyendo id");
		}

		logger.debug("Buscando información del cancha " + id);

		try {
			formulario = new FormField(service.getById(id));
		} catch (PersistenceException e) {
			logger.error("Error buscando información del cancha " + id);
			error.add(e);
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
		FieldService modifyService = new FieldService();

		Long id = -1L;
		Long idComplex = -1L;
		Boolean hasRoof = false;
		FloorType floor = FloorType.CONCRETE;
		FormHandler formulario = new FormField();

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

		try {
			hasRoof = Boolean.valueOf(request.getParameter("hasRoof"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de techo invalida");
		}

		try {
			floor = FloorType.valueOf(request.getParameter("floor"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de piso invalida");
		}

		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			error.add("Valores en la cancha incorrectos");
		}

		if (name == null) {
			error.add("Falta el nombre de la Cancha");
		}

		if (error.size() != 0) {
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
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

			for (int i = 0; i < daysOfWeek.length; i++) {

				aDay = request.getParameter(daysOfWeek[i]);
				System.out.println("parsin start hour for " + daysOfWeek[i]
						+ ": " + aDay);
				schedule.add(parser.parseDateTime(aDay));
				aDay = request.getParameter(daysOfWeek[i++]);
				System.out.println("parsin start hour for " + daysOfWeek[i]
						+ ": " + aDay);
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

			modifyService.addTimeTable(id, schedule.get(0), schedule.get(1),
					schedule.get(2), schedule.get(3), schedule.get(4), schedule
							.get(5), schedule.get(6), schedule.get(7), schedule
							.get(8), schedule.get(9), schedule.get(10),
					schedule.get(11), schedule.get(12), schedule.get(13));
			modifyService.updateField(id, name, description, hasRoof, floor);
			
			// TODO loguear los errores
			
		} catch (ElementExistsException ee) {
			error.add(ee);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
			
		} catch (PersistenceException e) {
			error.add(e);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
			
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
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);

	}

}
