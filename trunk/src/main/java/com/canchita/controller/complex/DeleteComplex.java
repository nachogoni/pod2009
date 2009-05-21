package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.service.ComplexService;

/**
 * Servlet implementation class DeleteComplex
 */
public class DeleteComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		Long id = null;
		
		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			delService.deleteComplex(id);
	    	request.getRequestDispatcher("/WEB-INF/views/ListComplex.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
