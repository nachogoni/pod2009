package com.canchita.controller.user.guest;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.LoginException;
import com.canchita.model.user.Registered;
import com.canchita.model.user.User;
import com.canchita.service.UserService;
import com.canchita.service.UserServiceProtocol;

/**
 * Servlet implementation class Login
 */
public class Login extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if ( isLogged(request,response) ) {
			return;
		}
		
		//Just render login page
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		if ( isLogged(request,response) ) {
			return;
		}
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		ErrorManager error = new ErrorManager();

		if (username == null) {
			error.add("Falta ingresar el nombre de usuario");
		}

		if (password == null) {
			error.add("Falta ingresar la contraseña del usuario");
		}
		
		if( error.size() != 0 ) {
			request.setAttribute("errorManager",error);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		UserServiceProtocol userService = new UserService();
		Registered user;
		try {
			user = userService.login(username,password);
		} catch (LoginException e) {
			logger.error(String.format("Login failed using %s:%s combination.",
					username, password));
			error.add(e);
			request.setAttribute("errorManager",error);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}
		
		request.getSession().setAttribute("user", user);
		
		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);
	}

	private boolean isLogged(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		User user = (User) request.getSession().getAttribute("user");
		
		if( ! user.getIsGuest() ) {
			UrlMapper.sendHome(request,response);
			return true;
		}
		
		return false;
	}

}
