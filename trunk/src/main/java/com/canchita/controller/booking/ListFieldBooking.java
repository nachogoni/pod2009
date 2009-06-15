package com.canchita.controller.booking;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Booking;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;

/**
 * Servlet implementation class ListBooking
 */
public class ListFieldBooking extends GenericServlet implements Servlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see GenericServlet#GenericServlet()
     */
    public ListFieldBooking() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		BookingServiceProtocol bookingService = new BookingService();
		List<Booking> bookings;
		
		ErrorManager error = new ErrorManager();

		Long id;
		try {
			id = Long.parseLong(request.getParameter("id")); 
		}
		catch(NumberFormatException e) {
			error.add("El identificador del complejo debe ser un número");
			this.failureGET(request, response, error);
			return;
		}
		try {
			bookings = bookingService.getBookableBookings(id);
		} catch (PersistenceException e) {
			error.add(e);
			this.failureGET(request, response, error);
			return;
		}

		request.setAttribute("bookings", bookings);
		request.setAttribute("bookingsLength", bookings.size());
		request.setAttribute("showComplex", false);
		request.setAttribute("showField", false);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		
	}


}
