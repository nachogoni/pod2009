package com.canchita.service;

import java.util.Collection;

import com.canchita.DAO.exception.ElementExistsException;
import com.canchita.model.booking.Booking;

public interface BookingServiceProtocol {

	public void saveBooking(Booking booking) throws ElementExistsException;
	
	void updateBooking(Booking booking);
	
	public void deleteBooking(Long id);
	
	public Collection<Booking> getComplexBookings(Long complexId);

	public Collection<Booking> getFieldBookings(Long fieldId);
	
}
