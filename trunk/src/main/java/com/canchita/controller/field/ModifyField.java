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
import com.canchita.model.exception.ElementNotExistsException;
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

		FormHandler formulario;
		logger.debug("GET request");

		FieldService service = new FieldService();
		Long id = null;
		formulario = new FormField();

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

		/* disabling complex select */
		formulario.setElementValue("idComplex", String.valueOf(id));
		(formulario.getElement("idComplex")).setDisable(true);
		
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
		FieldService modifyService = new FieldService();

		Long id = -1L;
		Boolean hasRoof = false;
		FloorType floor = FloorType.CONCRETE;
		BigDecimal price = null;
		BigDecimal bookingPercentage = null;
		Long number_of_players = null;

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
			error.add("Información sobre el tipo de techo inválida");
		}

		try {
			floor = FloorType.valueOf(request.getParameter("floor"));
		} catch (Exception nfe) {
			error.add("Información sobre el tipo de piso inválida");
		}

		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			error.add("Valores en la cancha incorrectos");
		}

		try {
			price = new BigDecimal(request.getParameter("price"));
		} catch (NumberFormatException nfe) {
			error.add("Error en el precio");
		}

		try {
			String param = request.getParameter("accontationPercentage");
			if (param != null && param != "" )
				bookingPercentage = new BigDecimal(param);
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
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		try {
			modifyService.updateField(id, name, description, number_of_players,
					hasRoof, floor, price, bookingPercentage);
		} catch (ElementNotExistsException e) {
			error.add("No se pudo modificar porque el elemento fue eliminado.");
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} catch (ElementExistsException e) {
			error.add(e);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} catch (PersistenceException e) {
			error.add("No se pudo modificar por un error de persistencia.");
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
