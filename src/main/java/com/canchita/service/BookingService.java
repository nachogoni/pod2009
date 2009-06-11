package com.canchita.service;

import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;

public class BookingService implements BookingServiceProtocol {

	public BookingService() {

	}

	@Override
	public void deleteBooking(Long id) throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);
		
		bookingDAO.delete(id);

	}

	@Override
	public Iterator<Booking> getBookedBookings(Long complexId) throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);
		
		return bookingDAO.getComplexBookings(complexId);
	}

	@Override
	public Iterator<Booking> getBookableBookings(Long fieldId) throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);
		
		return bookingDAO.getFieldBookings(fieldId);
	}

	@Override
	public void saveBooking(Long bookeableId, DateTime startTime, DateTime endTime)
			throws PersistenceException, BookingException {

		if( startTime.isAfter(endTime) ) {
			throw new BookingException("La fecha de fin debe ser mayor a la fecha de inicio");
		}
		
		if( startTime.isBeforeNow() || endTime.isBeforeNow() ) {
			throw new BookingException("Las reservas deben ser a partir de este instante");
		}
		
		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);
		
		Bookable bookable = fieldDAO.getById(bookeableId);
				
		Schedule schedule = new Schedule(startTime,endTime);
		
		bookable.book(schedule);
	}

}
