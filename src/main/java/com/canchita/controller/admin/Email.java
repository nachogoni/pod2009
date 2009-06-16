package com.canchita.controller.admin;

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
import com.canchita.model.exception.UserException;
import com.canchita.model.user.Registered;
import com.canchita.service.UserService;
import com.canchita.service.UserServiceProtocol;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class Profile
 */
public class Email extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public Email() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Registered user = this.getUser(request);
		FormHandler form = new FormEmail(user);

		request.setAttribute("formulario", form);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("POST request");

		FormHandler form = new FormEmail();

		/* Load form with request values */
		form.loadValues(request);

		if (!form.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} else {
			ErrorManager error = new ErrorManager();
			Registered user = this.getUser(request);
			String[] emails = request.getParameterValues("email");
			UserServiceProtocol userService = new UserService();

			// Se verifica que otro usuario no tenga esos mails.
			for (String email : emails) {
				try {
					userService.otherUserHasEmail(user, email);
				} catch (UserException e) {
					error.add(e);
					request.setAttribute("formulario", form);
					this.failurePOST(request, response, error);
					return;
				}
			}

			// Se actualizan los mails.
			try {
				userService.updateEmails(user, emails);

			} catch (UserException e) {
				error.add(e);
				request.setAttribute("formulario", form);
				this.failurePOST(request, response, error);
				return;
			}

			Map<String, String> map = new HashMap<String, String>();

			map.put("update", "true");

			UrlMapper.getInstance().redirectSuccess(this, request, response,
					UrlMapperType.POST, map);

		}

	}

}
