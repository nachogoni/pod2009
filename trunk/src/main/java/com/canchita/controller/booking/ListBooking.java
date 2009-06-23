package com.canchita.controller.booking;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.model.field.Field;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;
import com.canchita.views.helpers.form.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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

		// ordeno la lista
		List<Booking> list = new ArrayList<Booking>(bookings);
		String typesort = "";

		ArrayList<String> parametersSort = new ArrayList<String>();
		HashMap<String, Pair<Comparator<Booking>, Comparator<Booking>>> comparators = new HashMap<String, Pair<Comparator<Booking>, Comparator<Booking>>>();
		parametersSort.add("sortUserName");
		comparators.put("sortUserName", new Pair<Comparator<Booking>, Comparator<Booking>>(Booking.compareUserNames(true),Booking.compareUserNames(false)));
		parametersSort.add("sortField");
		comparators.put("sortField", new Pair<Comparator<Booking>, Comparator<Booking>>(Booking.compareFields(true),Booking.compareFields(false)));
		parametersSort.add("sortComplex");
		comparators.put("sortComplex", new Pair<Comparator<Booking>, Comparator<Booking>>(Booking.compareComplexs(true),Booking.compareComplexs(false)));
		parametersSort.add("sortState");
		comparators.put("sortState", new Pair<Comparator<Booking>, Comparator<Booking>>(Booking.compareStates(true),Booking.compareStates(false)));
		parametersSort.add("sortCost");
		comparators.put("sortCost", new Pair<Comparator<Booking>, Comparator<Booking>>(Booking.compareCosts(true),Booking.compareCosts(false)));
		parametersSort.add("sortPay");
		comparators.put("sortPay", new Pair<Comparator<Booking>, Comparator<Booking>>(Booking.comparePays(true),Booking.comparePays(false)));

		for (String e : parametersSort) {
			if ((typesort = request.getParameter(e)) != null) {
				Collections.sort(list, typesort.equals("ASC") ? comparators.get(e).getFirst() : comparators.get(e).getSecond());
				request.setAttribute(String.format("%sTypeR", e), typesort.equals("ASC") ? "DESC" : "ASC");
				request.setAttribute(String.format("%sType", e), typesort);
			} else {
				request.setAttribute(String.format("%sTypeR", e), "ASC");
			}
		}
		
		request.setAttribute("bookings", list);
		request.setAttribute("bookingsLength", bookings.size());
		request.setAttribute("showComplex", true);
		request.setAttribute("showField", true);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		
	}


}
