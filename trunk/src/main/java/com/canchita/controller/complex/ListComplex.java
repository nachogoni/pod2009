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
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.service.ComplexService;
import com.canchita.service.ComplexServiceProtocol;

/**
 * Servlet implementation class ListComplex
 */
public class ListComplex extends GenericServlet {
	private static final long serialVersionUID = 1L;

	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListComplex() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");
		
		String search = request.getParameter("search");
		Collection<Complex> complexes = null;
		int complexesSize = 0;
		
		ComplexServiceProtocol complexService = new ComplexService();
		
		ErrorManager errorManager = new ErrorManager();

		if( search == null ) {
			
			try {
				complexes = complexService.listComplex();
			} catch (PersistenceException e) {
				errorManager.add(e);
				
				request.setAttribute("searchError", errorManager);
				
				logger.error("Error intentando listar complejos: " + e.getMessage());
				
				UrlMapper.getInstance().forwardFailure(this, request, response,
						UrlMapperType.GET);
				
				return;			
			}
			complexesSize = complexes.size();
			
			request.setAttribute("complexes", complexes);
			request.setAttribute("complexesLength", complexesSize);
			
			logger.debug("Saliendo del controlador");
			
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);
			
			return;
		}
		
		if( (search.trim()).equals("") ) {
			
			
			errorManager.add("El criterio de búsqueda no puede estar vacío");
			
			request.setAttribute("searchError", errorManager);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;
		}
		
		try {
			logger.debug("Realizando búsqueda: " + search);
			
			complexes = complexService.listComplex(search);
			complexesSize = complexes.size();
		}
		catch(ValidationException e) {
			
			errorManager.add(e);
			
			request.setAttribute("searchError", errorManager);
			
			logger.info("Error en la búsqueda");
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;			
			
		}
		catch(Exception e) {
			complexes = null;
			complexesSize = -1;
			logger.error("Error en la búsqueda de complejos: " + e.getMessage());
		}

		request.setAttribute("complexes", complexes);
		
		/*
		 * TODO se hizo esto porque no funcionaba el tag
		 * fn:length de jstl. Investigar!
		 */
		
		request.setAttribute("complexesLength", complexesSize);
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		
		return;
	
		
	}

}
