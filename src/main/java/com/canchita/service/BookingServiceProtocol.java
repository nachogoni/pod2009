package com.canchita.service;

import java.util.Collection;
import java.util.Date;

import com.canchita.DAO.exception.ElementExistsException;
import com.canchita.model.booking.Booking;

public interface BookingServiceProtocol {

	public void saveBooking(Long fieldId, Date startTime, Date endTime ) throws ElementExistsException;
	
	public void deleteBooking(Long id);
	
	public Collection<Booking> getComplexBookings(Long complexId);

	public Collection<Booking> getFieldBookings(Long fieldId);
	
}
