package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;

/**
 * Servlet implementation class DeleteComplex
 */
public class DeleteComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(DeleteComplex.class.getName());
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComplex() {
		super();
		// TODO Auto-generated constructor stub
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
			logger.error("Error leyendo identificador");
		}

		ErrorManager error = new ErrorManager();
		
		try {
			logger.debug("Eliminando complejo " + id);
			delService.deleteComplex(id);
			
		} catch (ElementNotExistsException e) {
			error.add(e);
			e.printStackTrace();
		} catch (PersistenceException e) {
			error.add(e);
			e.printStackTrace();
		}
		
		/*
		 * TODO cambiar esto a un forward a la pagina get que ṕide confirmacion
		 */
		
		if( error.size() != 0 ) {
			logger.error("Error eliminando complejo con id: " + id);
			System.out.println("Salio todo mal");
			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.POST);

		}
		
		logger.debug("Complejo elimado: " + id);
		System.out.println("salio todo bien");
		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);

	}

}
