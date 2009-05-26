package com.canchita.controller.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.complex.AddComplex;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

/**
 * Servlet implementation class AdminHome
 */
public class AdminHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AdminHome.class.getName());
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHome() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO agarrar los datos del admin
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String user;
		String password;

		logger.debug("POST request");

		user = request.getParameter("username");
		password = request.getParameter("password");
		if(!user.equals("admin")) {
			logger.debug(String.format("Login failed using %s:%s combination.",
					user, password));
		}

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.POST);
		
		logger.debug("Saliendo del controlador");
	}
}
