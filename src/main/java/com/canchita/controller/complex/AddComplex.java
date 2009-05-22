package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.views.helpers.FormHandler;

/**
 * Servlet implementation class AddComplex
 */
public class AddComplex extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private FormHandler formulario;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComplex() {
		super();
		formulario = new FormHandler();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.formulario = new FormHandler();
		request.setAttribute("formulario", this.formulario);
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.formulario.loadValues(request);
		if (!this.formulario.validate()) {
			request.setAttribute("formulario", this.formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);

		}

	}

}
