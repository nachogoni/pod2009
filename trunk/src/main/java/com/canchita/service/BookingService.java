package com.canchita.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.user.CommonUser;

public class BookingService implements BookingServiceProtocol {

	public BookingService() {

	}

	@Override
	public void deleteBooking(Long id) throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		bookingDAO.delete(id);

	}

	@Override
	public Iterator<Booking> getBookedBookings(Long complexId)
			throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		return bookingDAO.getComplexBookings(complexId);
	}

	@Override
	public Iterator<Booking> getBookableBookings(Long fieldId)
			throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		return bookingDAO.getFieldBookings(fieldId);
	}

	@Override
	public Booking saveBooking(CommonUser user, Long bookeableId, DateTime startTime,
			DateTime endTime) throws PersistenceException, BookingException {

		if (startTime.isAfter(endTime)) {
			throw new BookingException(
					"La fecha de fin debe ser mayor a la fecha de inicio");
		}

		if (startTime.isBeforeNow() || endTime.isBeforeNow()) {
			throw new BookingException(
					"Las reservas deben ser a partir de este instante");
		}

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		Bookable bookable = fieldDAO.getById(bookeableId);

		Schedule schedule = new Schedule(startTime, endTime);

		return bookable.book(user,schedule);
		
	}

	@Override
	public List<Booking> saveManyBookings(CommonUser user, Long id, DateTime startTimeFrom,
			DateTime endTimeFrom, DateTime startTimeTo, DateTime endTimeTo) throws PersistenceException, BookingException {
		
		//TODO se asume que start y end ocupan un dia cada uno
		
		if( startTimeFrom.getDayOfWeek()!= startTimeTo.getDayOfWeek()) {
			throw new BookingException("Los días de la semana no coinciden");
		}
		
		long startDay = startTimeFrom.getDayOfYear() + (365 * startTimeFrom.getYear());
		long endDay = startTimeTo.getDayOfYear() + (365 * startTimeTo.getYear());

		long amountOfWeeks = ( endDay - startDay ) / DayOfWeek.WEEK_DAYS;
		
		DateTime startTime = startTimeFrom;
		DateTime endTime = endTimeFrom;
		List<Booking> bookings = new ArrayList<Booking>();
		
		for( long i = 0 ; i <= amountOfWeeks ; i++ ) {
			try {
				Booking booking = this.saveBooking(user, id, startTime, endTime);
				bookings.add(booking);
			}
			catch(ElementExistsException e) {
				//keep going
			}
			
			startTime = startTime.plusDays(DayOfWeek.WEEK_DAYS);
			endTime = endTime.plusDays(DayOfWeek.WEEK_DAYS);
		}
		
		return bookings;
	}

	@Override
	public Collection<Booking> getDownBookings() throws ValidationException,
			PersistenceException {
		
		BookingDAO booking = DAOFactory.get(DAO.BOOKING);

		return booking.getDownBookings();
	}

}
