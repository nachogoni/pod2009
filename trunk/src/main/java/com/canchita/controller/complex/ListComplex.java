package com.canchita.controller.complex;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Collection<Complex> complexes = (new ComplexService()).listComplex();
		request.setAttribute("complexes", complexes);
    	request.getRequestDispatcher("/WEB-INF/views/ListComplex.jsp").forward(request, response);

	}

}
