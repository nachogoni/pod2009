package com.canchita.controller.error;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class ErrorException
 */
public class ErrorException extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(ErrorException.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ErrorException() {
		super();
		// TODO Auto-generated constructor stub
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
