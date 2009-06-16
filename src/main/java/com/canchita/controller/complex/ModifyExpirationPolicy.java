package com.canchita.controller.complex;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.expiration.FormAddExpirationPolicy;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;
import com.canchita.views.helpers.form.FormHandler;

public class ModifyExpirationPolicy extends GenericServlet {
	private static final long serialVersionUID = 1L;

	private FormHandler form;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyExpirationPolicy() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		ComplexService service = new ComplexService();
		form = new FormAddExpirationPolicy();

		ErrorManager error = new ErrorManager();

		try {
			Long expirationId = Long.parseLong(request.getParameter("id"));
			form = new FormAddExpirationPolicy(service
					.getExpirationPolicy(expirationId));
		} catch (Exception e) {
			logger
					.error("Error buscando información de la política de expiración");
			error.add(e);
		}

		if (error.size() != 0) {
			request.setAttribute("formulario", this.form);

			request.setAttribute("errorManager", error);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			return;
		}

		/* Form is sent to the view */
		request.setAttribute("formulario", this.form);

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
		Integer scoreFrom = null;
		Integer scoreTo = null;
		Integer downBooking = null;
		Integer downDeposit = null;

		/* Errors from the past are deleted. */
		this.form.unsetErrors();

		/* Load form with request values */
		this.form.loadValues(request);

		if (!this.form.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		ErrorManager error = new ErrorManager();

		try {
			scoreFrom = Integer.valueOf(request.getParameter("scoreFrom"));
		} catch (Exception nfe) {
			error.add("Puntaje inválido");
		}

		try {
			scoreTo = Integer.valueOf(request.getParameter("scoreTo"));
		} catch (Exception nfe) {
			error.add("Puntaje inválido");
		}

		try {
			downBooking = Integer.valueOf(request.getParameter("downBooking"));
		} catch (Exception nfe) {
			error.add("Caída reservada inválida");
		}

		try {
			downDeposit = Integer.valueOf(request.getParameter("downDeposit"));
		} catch (Exception nfe) {
			error.add("Caída de reserva señada inválida");
		}
		
		if (downBooking < downDeposit) {
			error.add("Las horas en estado señado no pueden ser menores al estado reservado.");
		}
		
		if (scoreFrom > scoreTo) {
			error.add("El puntaje inicial no puede ser superior al final.");
		}

		if (error.size() != 0) {
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		try {
			ComplexService service = new ComplexService();
			service.updateExpirationPolicy(Long.valueOf(request
					.getParameter("id")), scoreFrom, scoreTo, downBooking,
					downDeposit);
		} catch (ElementNotExistsException e) {
			error.add(e);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} catch (PersistenceException e) {
			error.add(e);
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);

	}
}