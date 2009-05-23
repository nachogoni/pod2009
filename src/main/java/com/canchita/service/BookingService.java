package com.canchita.service;

import java.util.Collection;
import java.util.Date;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.BookingMemoryMock;
import com.canchita.DAO.exception.ElementExistsException;
import com.canchita.model.booking.Booking;

public class BookingService implements BookingServiceProtocol {

	private BookingMemoryMock bookingDAO;

	public BookingService() {
		bookingDAO = new BookingMemoryMock();
	}
	
	@Override
	public void deleteBooking(Long id) {
		
		bookingDAO.delete(id);		

	}

	@Override
	public Collection<Booking> getComplexBookings(Long complexId) {
		
		return bookingDAO.getComplexBookings(complexId);
	}

	@Override
	public Collection<Booking> getFieldBookings(Long fieldId) {
		
		return bookingDAO.getFieldBookings(fieldId);
	}

	@Override
	public void saveBooking(Long fieldId, Date startTime, Date endTime)
			throws ElementExistsException {
		// TODO Auto-generated method stub
		
	}

}
