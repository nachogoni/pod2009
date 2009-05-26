package com.canchita.controller.field;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.complex.FormAddComplex;
import com.canchita.controller.complex.ModifyComplex;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.FloorType;
import com.canchita.service.ComplexService;
import com.canchita.service.FieldService;
import com.canchita.views.helpers.FormHandler;

/**
 * Servlet implementation class ModifyField
 */
public class ModifyField extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	Logger logger = Logger.getLogger(ModifyField.class.getName());
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
		
		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error leyendo id");
		}

		try {
			logger.debug("Buscando información del cancha " + id);
			formulario = new FormField(service.getById(id));
			//request.setAttribute("field", service.getById(id));

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error buscando información del cancha " + id);
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

		try{
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			error.add("Valores en la cancha incorrectos");
		}
		


		if (name == null) {
			error.add("Falta el nombre de la Cancha");
		}	

		
		if (error.size() != 0) {
			request.setAttribute("error", error);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		try {
			modifyService.updateField(id, name, description, idComplex, hasRoof, floor);
		} catch (ElementNotExistsException e) {
			error.add(e);
			request.setAttribute("error", error);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}catch (PersistenceException e) {
			error.add(e);
			request.setAttribute("error", error);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		UrlMapper.getInstance().redirectSuccess(this, request, response, UrlMapperType.POST);
		
	}

}
