package com.canchita.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
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
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.UserException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.user.CommonUser;

public class BookingService implements BookingServiceProtocol {

	public BookingService() {

	}

	@Override
	public void cancelBooking(Long id) throws BookingException, PersistenceException, UserException {

		Booking booking  = this.getById(id);
		
		booking.cancel();

		ScoreSystemServiceProtocol scoreSystemService = new ScoreSystemService();

		ScoreSystem ss = scoreSystemService.getScoreSystem();

		ss.cancel(booking);

	}

	@Override
	public List<Booking> getBookedBookings(Long complexId)
			throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		return bookingDAO.getComplexBookings(complexId);
	}

	@Override
	public List<Booking> getBookableBookings(Long fieldId)
			throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		return bookingDAO.getFieldBookings(fieldId);
	}

	@Override
	public Booking saveBooking(CommonUser user, Long bookeableId,
			DateTime startTime, DateTime endTime) throws PersistenceException,
			BookingException, UserException {

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

		Booking booking = bookable.book(user, schedule);

		ScoreSystemServiceProtocol scoreSystemService = new ScoreSystemService();

		ScoreSystem ss = scoreSystemService.getScoreSystem();

		ss.book(booking);

		return booking;

	}

	@Override
	public List<Booking> saveManyBookings(CommonUser user, Long id,
			DateTime startTimeFrom, DateTime endTimeFrom, DateTime startTimeTo,
			DateTime endTimeTo) throws PersistenceException, BookingException,
			UserException {

		// TODO se asume que start y end ocupan un dia cada uno

		if (startTimeFrom.getDayOfWeek() != startTimeTo.getDayOfWeek()) {
			throw new BookingException("Los días de la semana no coinciden");
		}

		long startDay = startTimeFrom.getDayOfYear()
				+ (365 * startTimeFrom.getYear());
		long endDay = startTimeTo.getDayOfYear()
				+ (365 * startTimeTo.getYear());

		long amountOfWeeks = (endDay - startDay) / DayOfWeek.WEEK_DAYS;

		DateTime startTime = startTimeFrom;
		DateTime endTime = endTimeFrom;
		List<Booking> bookings = new ArrayList<Booking>();

		for (long i = 0; i <= amountOfWeeks; i++) {
			try {
				Booking booking = this
						.saveBooking(user, id, startTime, endTime);
				bookings.add(booking);
			} catch (ElementExistsException e) {
				// keep going
			}

			startTime = startTime.plusDays(DayOfWeek.WEEK_DAYS);
			endTime = endTime.plusDays(DayOfWeek.WEEK_DAYS);
		}

		return bookings;
	}

	@Override
	public Collection<Booking> getDownBookings(Long complexId)
			throws ValidationException, PersistenceException {

		BookingDAO booking = DAOFactory.get(DAO.BOOKING);

		return booking.getDownBookings(complexId);
	}

	@Override
	public Collection<Booking> getDownBookings() throws ValidationException,
			PersistenceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void fullPayBooking(Long id) throws BookingException, UserException {
		Booking booking = this.getById(id);
		
		ScoreSystemServiceProtocol scoreSystemService = new ScoreSystemService();
		
		ScoreSystem scoreSystem;
		try {
			scoreSystem = scoreSystemService.getScoreSystem();
		} catch (PersistenceException e) {
			throw new BookingException("No se pudo realizar el pago");
		}
		
		booking.pay(booking.getCost().subtract(booking.getPaid()),scoreSystem);
		
	}

	@Override
	public void payBooking(Long id, BigDecimal amount) throws BookingException, UserException {
		Booking booking = this.getById(id);

		ScoreSystemServiceProtocol scoreSystemService = new ScoreSystemService();
		
		ScoreSystem scoreSystem;
		try {
			scoreSystem = scoreSystemService.getScoreSystem();
		} catch (PersistenceException e) {
			throw new BookingException("No se pudo realizar el pago");
		}
		
		booking.pay(amount, scoreSystem);
	}
	
	private Booking getById(Long id) throws BookingException {
		BookingDAO bookingDAO;
		try {
			bookingDAO = DAOFactory.get(DAO.BOOKING);
		} catch (PersistenceException e) {
			throw new BookingException("No se pudo realizar el pago");
		}
		
		Booking booking;
		try {
			booking = bookingDAO.getById(id);
		} catch (ElementNotExistsException e) {
			throw new BookingException("No existe la reserva");
		}
		
		return booking;
	}

	@Override
	public List<Booking> getAllBookings() throws BookingException {
		
		BookingDAO bookingDAO;
		
		try {
			bookingDAO = DAOFactory.get(DAO.BOOKING);
		} catch (PersistenceException e) {
			throw new BookingException("No se pudo listar las reservas");
		}
		
		return bookingDAO.getAllBookings();
	}


}

