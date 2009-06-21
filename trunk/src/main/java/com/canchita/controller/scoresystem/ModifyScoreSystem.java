package com.canchita.controller.scoresystem;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.ScoreSystemService;
import com.canchita.views.helpers.form.FormHandler;

public class ModifyScoreSystem extends GenericServlet {
	private static final long serialVersionUID = 1L;

	private FormHandler form;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ModifyScoreSystem() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		ScoreSystemService service = new ScoreSystemService();
		form = new FormScoreSystem();

		ErrorManager error = new ErrorManager();

		try {
			form = new FormScoreSystem(service.getScoreSystem());
		} catch (PersistenceException e) {
			logger.error("Error buscando información del sistema de puntos");
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
		ScoreSystemService modifyService = new ScoreSystemService();

		Integer booking = null;
		Integer deposit = null;
		Integer pay = null;
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
			booking = Integer.valueOf(request.getParameter("booking"));
		} catch (Exception nfe) {
			error.add("Puntaje por reserva inválido");
		}

		try {
			deposit = Integer.valueOf(request.getParameter("deposit"));
		} catch (Exception nfe) {
			error.add("Puntaje por seña inválido");
		}

		try {
			pay = Integer.valueOf(request.getParameter("pay"));
		} catch (Exception nfe) {
			error.add("Puntaje por pago inválido");
		}

		try {
			downBooking = Integer.valueOf(request.getParameter("downBooking"));
		} catch (Exception nfe) {
			error.add("Puntaje por caída reservada inválido");
		}

		try {
			downDeposit = Integer.valueOf(request.getParameter("downDeposit"));
		} catch (Exception nfe) {
			error.add("Puntaje por caída de reserva señada inválido");
		}

		if (downDeposit <= downBooking)
			error.add("Una caída en estado señada debe restar más "
					+ "puntos que en estado reservada");

		if (downBooking <= booking)
			error.add("La caída en estado reservada debe restar más puntos que"
					+ " los asignados al reservar");

		if (downDeposit <= booking + deposit)
			error.add("La caída en estado señada debe restar más puntos que"
					+ " los asignados hasta el momento. La penalización por"
					+ " caída en estado señada debe ser mayor que "
					+ (booking + deposit));

		if (error.size() != 0) {
			request.setAttribute("errorManager", error);
			request.setAttribute("formulario", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		}

		try {
			modifyService.updateScoreSystem(booking, deposit, pay, downBooking,
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