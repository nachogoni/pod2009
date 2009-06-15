package com.canchita.controller.booking;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ListBooking
 */
public class ListBooking extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see GenericServlet#GenericServlet()
     */
    public ListBooking() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookingServiceProtocol bookingService = new BookingService();
		List<Booking> bookings;
		try {
			bookings = bookingService.getAllBookings();
		} catch (BookingException e) {
			ErrorManager error = new ErrorManager();
			error.add(e);
			this.failureGET(request, response, error);
			return;
		}

		request.setAttribute("bookings", bookings);
		request.setAttribute("bookingsLength", bookings.size());
		request.setAttribute("showComplex", true);
		request.setAttribute("showField", true);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		
	}


}
