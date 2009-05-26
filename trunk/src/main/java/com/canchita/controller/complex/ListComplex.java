package com.canchita.controller.complex;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.Level;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ValidationException;
import com.canchita.service.ComplexService;
import com.canchita.service.ComplexServiceProtocol;

/**
 * Servlet implementation class ListComplex
 */
public class ListComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(ListComplex.class.getName());
	
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
		
		if( search == null ) {
			
			complexes = complexService.listComplex();
			
			request.setAttribute("complexes", complexes);
			
			logger.debug("Saliendo del controlador");
			
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);
			
			return;
		}

		try {
			logger.debug("Realizando búsqueda: " + search);
			
			complexes = complexService.listComplex(search);
			complexesSize = complexes.size();
		}
		catch(ValidationException e) {
			ErrorManager errorManager = new ErrorManager();
			
			errorManager.add(e);
			
			request.setAttribute("searchError", errorManager);
			
			logger.error("Error en la búsqueda");
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;			
			
		}
		catch(Exception e) {
			complexes = null;
			complexesSize = -1;
			logger.error("Error en la búsqueda");
		}

		request.setAttribute("complexes", complexesSize);
		
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
