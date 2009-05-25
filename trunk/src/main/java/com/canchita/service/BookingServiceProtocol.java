package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.PersistenceException;


public interface BookingServiceProtocol {

	void saveBooking(Long bookeableId, DateTime startTime, DateTime endTime)
			throws PersistenceException;

	public void deleteBooking(Long id);

	public Iterator<Booking> getBookenBookings(Long bookerId);

	public Iterator<Booking> getBookeableBookings(Long bookeableId);

}
