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
import com.canchita.service.FieldService.FieldBuilder;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class AddField
 */
public class AddField extends GenericServlet {
	private static final long serialVersionUID = 1L;
	private FormHandler formulario;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddField() {
		super();
		formulario = new FormField();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		String complexId = request.getParameter("id");

		Long id = null;

		try {
			id = Long.parseLong(complexId);

		} catch (NumberFormatException nfe) {

			ErrorManager error = new ErrorManager();

			error.add("Complejo invalido");

			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);

			return;

		}

		/* Get Form */
		formulario = new FormField();

		formulario.setElementValue("idComplex", id.toString());

		/* Form is sent to the view */
		request.setAttribute("formulario", this.formulario);

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

		Long idComplex = null;
		Boolean hasRoof = null;
		FloorType floor = null;

		logger.debug("POST request");

		/* Errors from the past are deleted. */
		this.formulario.unsetErrors();

		/* Load form with request values */
		this.formulario.loadValues(request);

		if (!this.formulario.isValid()) {
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
			idComplex = Long.parseLong(request.getParameter("idComplex"));
		} catch (NumberFormatException nfe) {
			error.add("Complejo invalido");
		}

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

		if (name == null) {
			error.add("Falta el nombre de la Cancha");
		}

		if (error.size() != 0) {
			request.setAttribute("error", error);
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

		if (error.size() != 0) {
			request.setAttribute("error", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		try {
			FieldBuilder.Build(name, description, idComplex, hasRoof, floor);

	/**		FieldBuilder.addTimeTable(schedule.get(0), schedule.get(1),
					schedule.get(2), schedule.get(3), schedule.get(4), schedule
							.get(5), schedule.get(6), schedule.get(7), schedule
							.get(8), schedule.get(9), schedule.get(10),
					schedule.get(11), schedule.get(12), schedule.get(13));
*/
			FieldBuilder.saveField();

		} catch (ElementExistsException ee) {
			error.add(ee);
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		} catch (IllegalArgumentException e) {
			error.add(e);
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		} catch (PersistenceException e) {
			error.add(e);
			request.setAttribute("formulario", formulario);
			this.failure(request, response, error);
			return;
		} catch (Exception e) {
			error.add(e);
			e.printStackTrace();
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
