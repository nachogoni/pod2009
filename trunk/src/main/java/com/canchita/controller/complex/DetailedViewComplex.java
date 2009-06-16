package com.canchita.controller.complex;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.field.Field;
import com.canchita.service.ComplexService;
import com.canchita.service.FieldService;

/**
 * Servlet implementation class DetailedViewComplex
 */
public class DetailedViewComplex extends GenericServlet {
	private static final long serialVersionUID = 1L;


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailedViewComplex() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		ComplexService service = new ComplexService();
		FieldService fieldService = new FieldService();
		Collection<Field> fields = null;
		Long id = null;

		ErrorManager error = new ErrorManager();
		
		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			logger.error("Error leyendo id de complejo: " + e.getMessage());
			error.add("El identificador debe ser numérico");
		}

		try {
			logger.debug("Buscando detalles del complejo con id " + id);
			request.setAttribute("complex", service.getById(id));

		} catch (Exception e) {
			error.add(e);
			logger.error("Error buscando detalles del complejo con id " + id);
		}

		if( error.size() != 0 ) {
			
			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;
		}
		
		try {
			logger.debug("Listando canchas para el complejo con id " + id);
			fields = fieldService.listField(id);
			logger.debug(fields);
			request.setAttribute("fields", fields);
		} catch (Exception e) {
			error.add(e);
			logger.error("Error listando canchas para el complejo " + id);
			
			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;
		}
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

}
