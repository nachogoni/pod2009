package com.canchita.service;

import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;


public interface BookingServiceProtocol {

	void saveBooking(Long bookeableId, DateTime startTime, DateTime endTime)
			throws PersistenceException, BookingException;

	public void deleteBooking(Long id) throws PersistenceException;

	public Iterator<Booking> getBookedBookings(Long bookerId) throws PersistenceException;

	public Iterator<Booking> getBookableBookings(Long bookeableId) throws PersistenceException;

}
