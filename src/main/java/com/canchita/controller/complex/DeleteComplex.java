package com.canchita.controller.complex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ComplexException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;

/**
 * Servlet implementation class DeleteComplex
 */
public class DeleteComplex extends GenericServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComplex() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		ComplexService delService = new ComplexService();

		logger.debug("POST request");
		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error leyendo identificador del complejo: " + e.getMessage());
		}

		ErrorManager error = new ErrorManager();
		
		try {
			logger.debug("Eliminando complejo " + id);
			delService.deleteComplex(id);
			
		} catch (ElementNotExistsException e) {
			error.add(e);
		} catch (PersistenceException e) {
			error.add(e);
		} catch (ComplexException e) {
			Map<String, String> map = new HashMap<String, String>();

			map.put("hasBookings", "true");
			
			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.POST,map);
			
			return;
		}
		
		if( error.size() != 0 ) {
			logger.error("Error eliminando complejo con id: " + id);
			
			Map<String, String> map = new HashMap<String, String>();

			map.put("delete", "false");
			
			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.POST,map);
			return;

		}
		
		logger.debug("Complejo elimado: " + id);
		
		Map<String, String> map = new HashMap<String, String>();

		map.put("delete", "true");
		
		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST,map);

	}

}
