package com.canchita.controller.error;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class ErrorException
 */
public class ErrorException extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ErrorException() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.error("Error generado al acceder a " + request.getRequestURI());
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
	}

}
