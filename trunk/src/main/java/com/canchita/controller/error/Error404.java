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
 * Servlet implementation class Error404
 */
public class Error404 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Logger logger = Logger.getLogger(Error404.class.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Error404() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.error("Recurso inexistente: " + request.getRequestURI());

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
	}

}
