package com.canchita.controller;

import java.io.IOException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.canchita.controller.admin.AdminHome;

/**
 * Servlet implementation class GenericServlet
 */
public class GenericServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String LOG4J_PROPERTIES = "log4j.properties";

	protected Logger logger;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public GenericServlet() {
		super();
		URL url = Thread.currentThread().getContextClassLoader().getResource(
				LOG4J_PROPERTIES);
		PropertyConfigurator.configure(url);

		this.logger = Logger.getLogger(this.getClass().getName());
	}

}
