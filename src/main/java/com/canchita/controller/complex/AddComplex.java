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

		ComplexService addService = new ComplexService();
		
		//TODO: Migrar a ComplexForm
		
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		
		String zipCode = request.getParameter("zipCode");
		String town = request.getParameter("town");
		String state = request.getParameter("state");
		String neighbourhood = request.getParameter("neighbourhood");
		String country = request.getParameter("country");
		String address = request.getParameter("address");
		
		Integer booking = Integer.parseInt(request.getParameter("booking"));
		Integer deposit = Integer.parseInt(request.getParameter("deposit"));
		Integer pay = Integer.parseInt(request.getParameter("pay"));
		Integer downBooking = Integer.parseInt(request.getParameter("downBooking"));
		Integer downDeposit = Integer.parseInt(request.getParameter("downDeposit"));
		
		request.removeAttribute("search");
		
		Long id = addService.saveComplex(name, description, address, zipCode, neighbourhood, town, state, country);
		
		addService.addScoreSystem(id, booking, deposit, pay, downBooking, downDeposit);
		
		UrlMapper.getInstance().forwardSuccess(this, request, response, UrlMapperType.POST);
		
		
	}

}
