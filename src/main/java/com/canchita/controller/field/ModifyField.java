package com.canchita.controller.field;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.FloorType;
import com.canchita.service.FieldService;
import com.canchita.views.helpers.FormHandler;

/**
 * Servlet implementation class ModifyField
 */
public class ModifyField extends GenericServlet {
	private static final long serialVersionUID = 1L;
	
	private FormHandler formulario;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyField() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
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

		if( error.size() != 0 ) {
			request.setAttribute("formulario", this.formulario);
			
			request.setAttribute("errorManager", error);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			return;
		}
		
		/* Form is sent to the view*/
		request.setAttribute("formulario", this.formulario);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		logger.debug("Saliendo del controlador");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FieldService modifyService = new FieldService();
		
		Long id = -1L;
		Long idComplex = -1L;
		Boolean hasRoof = false;
		FloorType floor = FloorType.CONCRETE;

		
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
			hasRoof = Boolean.valueOf(request.getParameter("hasRoof"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de techo invalida");
		}
		
		try{
			floor = FloorType.valueOf(request.getParameter("floor"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de piso invalida");
		}

		try{
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
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		try {
			modifyService.updateField(id, name, description, hasRoof, floor);
		} catch (ElementNotExistsException e) {
			error.add(e);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}catch (PersistenceException e) {
			error.add(e);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		UrlMapper.getInstance().redirectSuccess(this, request, response, UrlMapperType.POST);
		
	}

}
