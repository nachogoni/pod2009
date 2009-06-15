package com.canchita.controller.user.guest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.RegisterException;
import com.canchita.service.UserService;
import com.canchita.service.UserServiceProtocol;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class Register
 */
public class Register extends GenericServlet {
	private static final long serialVersionUID = 1L;

	private FormHandler form;

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public Register() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		/* Get Form */
		form = new FormRegister();

		/* Form is sent to the view */
		request.setAttribute("form", this.form);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		logger.debug("Saliendo del controlador");

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("POST request");
		/* Errors from the past are deleted. */
		this.form.unsetErrors();

		/* Load form with request values */
		this.form.loadValues(request);

		if (!this.form.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("form", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} else {
			ErrorManager error = new ErrorManager();

			String username = request.getParameter("name");
			String email = request.getParameter("email");

			String password = request.getParameter("pass");
			String passwordAgain = request.getParameter("passAgain");

			if (username == null) {
				error.add("Falta el nombre de Usuario");
			}
			if (email == null) {
				error.add("Falta la dirección de correo electrónico");
			}
			if (password == null) {
				error.add("Falta ingresar la contraseña");
			}
			if (passwordAgain == null) {
				error.add("Falta reingresar la contraseña");
			}

			if (!password.equals(passwordAgain)) {
				error.add("Las contraseñas deben coincidir");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("form", form);
				this.failure(request, response, error);
				return;
			}

			String baseUrl = (String) request.getAttribute("baseURL");

			UserServiceProtocol userService = new UserService();

			try {
				userService.register(username, password, email, baseUrl, this
						.getServletContext().getRealPath(
								"/WEB-INF/resources/mails"));
			} catch (RegisterException e) {
				logger.debug("Error al realizar la registración");
				error.add(e);
				e.printStackTrace();
				request.setAttribute("form", form);
				this.failure(request, response, error);
				return;
			}

			Map<String, String> map = new HashMap<String, String>();

			map.put("register", "true");

			UrlMapper.getInstance().redirectSuccess(this, request, response,
					UrlMapperType.POST, map);

		}
	}

	private void failure(HttpServletRequest request,
			HttpServletResponse response, ErrorManager error)
			throws ServletException, IOException {
		request.setAttribute("errorManager", error);

		UrlMapper.getInstance().forwardFailure(this, request, response,
				UrlMapperType.POST);

	}

}
