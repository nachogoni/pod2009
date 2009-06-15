package com.canchita.controller.field;

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
import com.canchita.service.FieldService;
import com.canchita.views.helpers.form.FormHandler;

public class AddFieldExpirationPolicy extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public AddFieldExpirationPolicy() {
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
		FieldService service = new FieldService();
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

			String strScoreFrom = request.getParameter("scoreFrom");
			String strScoreTo = request.getParameter("scoreTo");
			String strDownBooking = request.getParameter("downBooking");
			String strDownDeposit = request.getParameter("downDeposit");

			if (strScoreFrom == null) {
				error.add("Falta el puntaje inicial");
			}
			if (strScoreTo == null) {
				error.add("Falta el puntaje final");
			}
			if (strDownBooking == null) {
				error.add("Falta cuantas horas antes se cae la reserva");
			}
			if (strDownDeposit == null) {
				error
						.add("Falta cuantas horas antes se cae la reserva estando señada");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}

			Integer scoreFrom = null;
			Integer scoreTo = null;
			Integer downBooking = null;
			Integer downDeposit = null;
			Long id = null;

			try {

				scoreFrom = Integer.parseInt(strScoreFrom);
				scoreTo = Integer.parseInt(strScoreTo);
				downBooking = Integer.parseInt(strDownBooking);
				downDeposit = Integer.parseInt(strDownDeposit);
				id = Long.parseLong(request.getParameter("id"));

			} catch (NumberFormatException nfe) {
				error.add("Valores para el sistema de reservas incorrectos");
			}

			if (error.size() != 0) {
				logger.debug("Error en el formulario");
				request.setAttribute("formulario", formulario);
				this.failure(request, response, error);
				return;
			}
			

			try {
				service.setExpirationPolicy(id, scoreFrom, scoreTo, downBooking, downDeposit);
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
