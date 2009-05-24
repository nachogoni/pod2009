package com.canchita.controller.complex;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.complex.Complex;
import com.canchita.service.ComplexService;

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

		Collection<Complex> complexes = (new ComplexService()).listComplex();
		request.setAttribute("complexes", complexes);
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

}
