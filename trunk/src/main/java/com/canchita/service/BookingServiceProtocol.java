package com.canchita.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.UserException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.user.CommonUser;

public interface BookingServiceProtocol {

	Booking saveBooking(CommonUser user, Long bookeableId, DateTime startTime,
			DateTime endTime) throws PersistenceException, BookingException,
			UserException;

	void cancelBooking(Long id) throws BookingException, PersistenceException, UserException;

	void payBooking(Long id, BigDecimal amount)
	throws BookingException, UserException;
	
	void fullPayBooking(Long id) throws BookingException, UserException;
	
	public List<Booking> getBookedBookings(Long bookerId)
			throws PersistenceException;

	public List<Booking> getBookableBookings(Long bookeableId)
			throws PersistenceException;

	List<Booking> saveManyBookings(CommonUser user, Long id,
			DateTime startTimeFrom, DateTime endTimeFrom, DateTime startTimeTo,
			DateTime endTimeTo) throws PersistenceException, BookingException,
			UserException;

	public Collection<Booking> getDownBookings() throws ValidationException,
			PersistenceException;

	Collection<Booking> getDownBookings(Long complexId)
			throws ValidationException, PersistenceException;

	public Collection<Booking> getDownBookings(String neighbourhood, Long listCount)
			throws ValidationException, PersistenceException;
	List<Booking> getAllBookings() throws BookingException;

}
