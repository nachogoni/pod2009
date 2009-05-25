package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.service.ComplexService;
import com.canchita.views.helpers.ComplexForm;
import com.canchita.views.helpers.FormHandler;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class AddComplex
 */
public class AddComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private FormHandler formulario;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComplex() {
		super();
		formulario = new ComplexForm();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//TODO: Migrar a ComplexForm
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		//TODO: Migrar a ComplexForm
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		String zipCode = request.getParameter("zipCode");
		String town = request.getParameter("town");
		String state = request.getParameter("state");
		String neighbourhood = request.getParameter("neighbourhood");
		String country = request.getParameter("country");
		String address = request.getParameter("address");
		
		request.removeAttribute("search");
		
		(new ComplexService()).saveComplex(name, description, address, zipCode, neighbourhood, town, state, country);
		UrlMapper.getInstance().forwardSuccess(this, request, response, UrlMapperType.POST);
		
	}

}
