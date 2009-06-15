package com.canchita.controller.booking;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.UserException;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FullPay
 */
public class FullPay extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see GenericServlet#GenericServlet()
	 */
	public FullPay() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
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
			Map<String, String> map = new HashMap<String, String>();

			map.put("fullpay", "false");

			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.POST, map);
			return;
		}

		BookingServiceProtocol bookingService = new BookingService();

		try {
			bookingService.fullPayBooking(id);
		} catch (BookingException e) {
			error.add(e);
		} catch (UserException e) {
			error.add(e);
		}

		if (error.size() != 0) {
			Map<String, String> map = new HashMap<String, String>();

			map.put("fullpay", "false");

			UrlMapper.getInstance().redirectFailure(this, request, response,
					UrlMapperType.POST, map);
			return;
		}
		
		Map<String, String> map = new HashMap<String, String>();

		map.put("fullpay", "true");

		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST, map);
	}

}
