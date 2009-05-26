package com.canchita.controller.field;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.FloorType;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;
import com.canchita.views.helpers.FormHandler;

/**
 * Servlet implementation class AddField
 */
public class AddField extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AddField.class.getName());
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
		
		try{
			id = Long.parseLong(complexId);
			
		} catch (NumberFormatException nfe) {
			
			ErrorManager error = new ErrorManager();
			
			error.add("Complejo invalido");
			
			request.setAttribute("errorManager", error);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;

		}
		
		/*Get Form*/
		formulario = new FormField();

		formulario.setElementValue("idComplex", id.toString());
		
		/* Form is sent to the view*/
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
		
		/*Errors from the past are deleted.*/
		this.formulario.unsetErrors();

		/*Load form with request values*/
		this.formulario.loadValues(request);
		
		if (!this.formulario.isValid())
		{
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}
		
		ErrorManager error = new ErrorManager();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		try{
			idComplex = Long.parseLong(request.getParameter("idComplex"));
		} catch (NumberFormatException nfe) {
			error.add("Complejo invalido");
		}
		
		try{
			hasRoof = Boolean.valueOf(request.getParameter("hasRoof"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de techo invalida");
		}
		
		try{
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
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		FieldServiceProtocol fieldService = new FieldService();
		System.out.println(name);
		try {
			fieldService.saveField(name, description, idComplex, hasRoof, floor);
		} catch (PersistenceException e) {
			error.add(e);
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
