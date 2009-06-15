package com.canchita.controller.complex;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Expiration;
import com.canchita.service.ComplexService;
import com.canchita.service.ComplexServiceProtocol;

public class ListExpirationPolicy extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListExpirationPolicy() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		String id = request.getParameter("id");

		ComplexServiceProtocol complexService = new ComplexService();

		ErrorManager errorManager = new ErrorManager();

		try {
			Collection<Expiration> list = complexService.listExpirationPolicies(Long
					.parseLong(id));
			request.setAttribute("policies", list);
			request.setAttribute("quantity", list.size());
			
		} catch (NumberFormatException e) {
			errorManager.add("El identificador de complejo debe ser numérico.");
			request.setAttribute("listError", errorManager);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);

			return;
		} catch (Exception e) {
			errorManager.add("Error listando políticas de expiración.");
			request.setAttribute("listError", errorManager);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);

			return;
		}

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		return;

	}

}
