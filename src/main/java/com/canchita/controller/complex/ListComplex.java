package com.canchita.controller.complex;

import java.io.IOException;
import java.util.Collection;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		String search = request.getParameter("search");
		Collection<Complex> complexes = null;
		int complexesSize = 0;
		
		ComplexServiceProtocol complexService = new ComplexService();
		
		if( search == null ) {
			
			complexes = complexService.listComplex();
			
			request.setAttribute("complexes", complexes);
			
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);
			
			return;
		}

		try {
			
			complexes = complexService.listComplex(search);
			complexesSize = complexes.size();
		}
		catch(ValidationException e) {
			ErrorManager errorManager = new ErrorManager();
			
			errorManager.add(e);
			
			request.setAttribute("searchError", errorManager);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;			
			
		}
		catch(Exception e) {
			complexes = null;
			complexesSize = -1;
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
