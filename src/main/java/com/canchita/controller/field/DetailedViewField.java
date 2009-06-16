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
import com.canchita.service.FieldService;

/**
 * Servlet implementation class DetailedViewComplex
 */
public class DetailedViewField extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DetailedViewField() {
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

		FieldService service = new FieldService();
		Long id = null;

		ErrorManager error = new ErrorManager();
		
		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			logger.error("Error leyendo id de cancha");
			error.add("El identificador debe ser numérico");
		}

		try {
			logger.debug("Buscando detalles de la cancha con id " + id);
			request.setAttribute("field", service.getById(id));

		} catch (Exception e) {
			error.add(e);
			logger.error("Error buscando detalles de la cancha con id " + id + ": " + e.getMessage());
		}

		if( error.size() != 0 ) {
			
			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;
		}

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

}
