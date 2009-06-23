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
import com.canchita.service.FieldService;

public class DeleteFieldExpirationPolicy extends GenericServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteFieldExpirationPolicy() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		FieldService delService = new FieldService();

		logger.debug("POST request");
		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error leyendo identificador de política");
		}

		ErrorManager error = new ErrorManager();
		
		try {
			logger.debug("Eliminando política " + id);
			delService.deleteExpirationPolicy(id);
			
		} catch (ElementNotExistsException e) {
			error.add(e);
			e.printStackTrace();
		} catch (PersistenceException e) {
			error.add(e);
			e.printStackTrace();
		}
		
		if( error.size() != 0 ) {
			logger.error("Error eliminando política con id: " + id);
			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.POST);

		}
		
		logger.debug("Política eliminada: " + id);
		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);

	}

}
