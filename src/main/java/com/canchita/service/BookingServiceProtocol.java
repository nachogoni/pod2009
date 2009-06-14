package com.canchita.service;

import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.CommonUser;


public interface BookingServiceProtocol {

	Booking saveBooking(CommonUser user, Long bookeableId, DateTime startTime, DateTime endTime)
			throws PersistenceException, BookingException;

	public void deleteBooking(Long id) throws PersistenceException;

	public Iterator<Booking> getBookedBookings(Long bookerId) throws PersistenceException;

	public Iterator<Booking> getBookableBookings(Long bookeableId) throws PersistenceException;

	List<Booking> saveManyBookings(CommonUser user, Long id, DateTime startTimeFrom,
			DateTime endTimeFrom, DateTime startTimeTo, DateTime endTimeTo) throws PersistenceException, BookingException;

}
