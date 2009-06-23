package com.canchita.controller.field;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.FloorType;
import com.canchita.service.FieldService.FieldBuilder;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class AddField
 */
public class AddField extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddField() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		FormHandler formulario = null;
		String complexId = request.getParameter("id");

		Long id = null;

		try {
			id = Long.parseLong(complexId);

		} catch (NumberFormatException nfe) {
			id = 1L;
			/*
			 * ErrorManager error = new ErrorManager();
			 * 
			 * error.add("Complejo invalido");
			 * 
			 * request.setAttribute("errorManager", error);
			 * 
			 * UrlMapper.getInstance().forwardFailure(this, request, response,
			 * UrlMapperType.GET);
			 * 
			 * return;
			 */
		}

		/* Get Form */
		formulario = new FormField();

		formulario.setElementValue("idComplex", id.toString());

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

		FormHandler formulario = new FormField();
		Long idComplex = null;
		Boolean hasRoof = null;
		FloorType floor = null;
		BigDecimal price = null;
		BigDecimal bookingPercentage = null;
		Long number_of_players = null;

		logger.debug("POST request");

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

		try {
			price = new BigDecimal(request.getParameter("price"));
		} catch (NumberFormatException nfe) {
			error.add("Error en el precio");
		}

		try {
			String percentage = request.getParameter("accontationPercentage");
			if (percentage != null && percentage != "")
				bookingPercentage = new BigDecimal(request
						.getParameter("accontationPercentage"));
			else
				bookingPercentage = null;
		} catch (NumberFormatException nfe) {
			error.add("Error en el porcentaje de seña");
		}

		try {
			number_of_players = Long.parseLong(request
					.getParameter("cantPlayers"));
		} catch (NumberFormatException nfe) {
			error.add("Error en cantidad de jugadores");
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

		try {
			FieldBuilder.Build(name, description, idComplex, hasRoof, floor,
					price, number_of_players, bookingPercentage);

			FieldBuilder.saveField();

		} catch (ElementExistsException ee) {
			error.add("No se puede agregar. La cancha ya existe.");
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
