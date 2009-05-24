package com.canchita.service;

import java.util.Collection;
import org.joda.time.DateTime;

import com.canchita.DAO.exception.ElementExistsException;
import com.canchita.model.booking.Booking;

public interface BookingServiceProtocol {

	void saveBooking(Long fieldId, DateTime startTime, DateTime endTime)
			throws ElementExistsException;

	public void deleteBooking(Long id);

	public Collection<Booking> getComplexBookings(Long complexId);

	public Collection<Booking> getFieldBookings(Long fieldId);

}
