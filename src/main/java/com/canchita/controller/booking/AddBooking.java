package com.canchita.controller.booking;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.views.helpers.ComplexForm;
import com.canchita.views.helpers.FormHandler;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class AddBooking
 */
public class AddBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBooking() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.getAttribute("id");

		
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		


	}

}
