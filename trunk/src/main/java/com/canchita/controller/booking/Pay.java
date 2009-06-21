package com.canchita.controller.booking;

import java.io.IOException;
import java.math.BigDecimal;
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
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.UserException;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;
import com.canchita.views.helpers.form.FormHandler;

/**
 * Servlet implementation class Profile
 */
public class Pay extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public Pay() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		ErrorManager error = new ErrorManager();

		String bookingId = request.getParameter("id");

		if (bookingId == null) {
			error.add("Falta el identificador de la reserva");
		}

		Long id = null;
		try {
			id = Long.parseLong(bookingId);
		} catch (NumberFormatException e) {
			error.add(e);
		}

		if (error.size() != 0) {
			this.failureGET(request, response, error);
			return;
		}

		FormHandler form = new FormPay(id);

		request.setAttribute("form", form);

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

		FormHandler form = new FormPay();

		/* Load form with request values */
		form.loadValues(request);

		if (!form.isValid()) {
			logger.debug("Formulario inválido");
			request.setAttribute("form", form);
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.POST);
			return;
		} else {
			ErrorManager error = new ErrorManager();

			BookingServiceProtocol bookingService = new BookingService();
			boolean exceeded = false;
			
			try {
				Long id = Long.parseLong(request.getParameter("id"));
				BigDecimal amount = new BigDecimal(request.getParameter("amount"));
				exceeded = bookingService.payBooking(id, amount);
			} catch (UserException e) {
				error.add(e);
				request.setAttribute("form", form);
				this.failurePOST(request, response, error);
			}
			catch(BookingException e) {
				error.add(e);
				request.setAttribute("form", form);
				this.failurePOST(request, response, error);				
			}

			Map<String, String> map = new HashMap<String, String>();

			map.put("pay", "true");
			map.put("exceeded", Boolean.toString(exceeded));
			
			UrlMapper.getInstance().redirectSuccess(this, request, response,
					UrlMapperType.POST, map);

		}

	}

}
