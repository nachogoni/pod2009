package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.memorymock.BookingMemoryMock;
import com.canchita.DAO.memorymock.FieldMemoryMock;
import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

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
	public Iterator<Booking> getBookenBookings(Long complexId) {

		return bookingDAO.getComplexBookings(complexId);
	}

	@Override
	public Iterator<Booking> getBookeableBookings(Long fieldId) {

		return bookingDAO.getFieldBookings(fieldId);
	}

	@Override
	public void saveBooking(Long bookeableId, DateTime startTime, DateTime endTime)
			throws PersistenceException, BookingException {

		FieldDAO fieldDAO = new FieldMemoryMock();
		
		Bookable bookable = fieldDAO.getById(bookeableId);
				
		Schedule schedule = new Schedule(startTime,endTime);
		
		bookable.book(schedule);
	}

}
