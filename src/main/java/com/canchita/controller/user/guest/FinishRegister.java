package com.canchita.controller.user.guest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.RegisterException;
import com.canchita.model.user.Registered;
import com.canchita.service.UserService;
import com.canchita.service.UserServiceProtocol;

/**
 * Servlet implementation class FinishRegister
 */
public class FinishRegister extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public FinishRegister() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String hash = request.getParameter("hash");

		if (hash == null) {
			Map<String, String> map = new HashMap<String, String>();

			map.put("register", "false");

			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.GET, map);
			return;
		}

		UserServiceProtocol userService = new UserService();

		Registered user;
		try {
			user = userService.confirmateHash(hash);
		} catch (RegisterException e) {
			Map<String, String> map = new HashMap<String, String>();

			map.put("register", "false");

			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.GET, map);
			return;
		}

		// If everything was ok we login the user
		request.getSession().setAttribute("user", user);

		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.GET);
	}

}
