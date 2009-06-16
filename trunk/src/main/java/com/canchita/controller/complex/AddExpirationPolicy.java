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
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ComplexService;
import com.canchita.views.helpers.form.FormHandler;

public class AddExpirationPolicy extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public AddExpirationPolicy() {
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
		FormHandler formulario = new FormAddExpirationPolicy();

		/* Form is sent to the view */
		request.setAttribute("formulario", formulario);

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
		ComplexService service = new ComplexService();
		FormHandler formulario = new FormAddExpirationPolicy();

		/* Load form with request values */
		formulario.loadValues(request);

		if (!formulario.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("formulario", formulario);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} else {
			ErrorManager error = new ErrorManager();

			String scoreFrom = request.getParameter("scoreFrom");
			String scoreTo = request.getParameter("scoreTo");
			String downBooking = request.getParameter("downBooking");
			String downDeposit = request.getParameter("downDeposit");

			if (scoreFrom == null) {
				error.add("Falta el puntaje inicial");
			}
			if (scoreTo == null) {
				error.add("Falta el puntaje final");
			}
			if (downBooking == null) {
				error.add("Falta cuantas horas antes se cae la reserva");
			}
			if (downDeposit == null) {
				error
						.add("Falta cuantas horas antes se cae la reserva estando señada");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}

			Integer scoreFrom1 = null;
			Integer scoreTo1 = null;
			Integer downBooking1 = null;
			Integer downDeposit1 = null;
			Long id = null;

			try {

				scoreFrom1 = Integer.parseInt(scoreFrom);
				scoreTo1 = Integer.parseInt(scoreTo);
				downBooking1 = Integer.parseInt(downBooking);
				downDeposit1 = Integer.parseInt(downDeposit);
				id = Long.parseLong(request.getParameter("id"));

			} catch (NumberFormatException nfe) {
				error.add("Valores para el sistema de reservas incorrectos");
			}
			
			if (scoreFrom1 > scoreTo1) {
				error.add("El puntaje inicial no puede ser superior al final.");
			}

			if (downBooking1 < downDeposit1) {
				error.add("Las horas en estado señado no pueden ser menores al estado reservado.");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}
			

			try {
				service.setExpirationPolicy(id, scoreFrom1, scoreTo1, downBooking1, downDeposit1);
			} catch (PersistenceException e) {
				// TODO Auto-generated catch block
				error.add("Error agregando política de expiración.");
			}
			
			if (error.size() != 0) {
				logger.debug("Error al guardar la política de expiración");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}

			UrlMapper.getInstance().redirectSuccess(this, request, response,
					UrlMapperType.POST);

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
