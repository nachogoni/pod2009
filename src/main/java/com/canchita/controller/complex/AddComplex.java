package com.canchita.controller.complex;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.views.helpers.ComplexForm;
import com.canchita.views.helpers.FormHandler;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;

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
		formulario = new ComplexForm();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.formulario = new ComplexForm();
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

		/* Errors from the past are deleted */
		this.formulario.unsetErrors();

		/* Form get loaded with POST */
		this.formulario.loadValues(request);

		if (!this.formulario.isValid()) {
			request.setAttribute("formulario", this.formulario);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
		}

	}

}
