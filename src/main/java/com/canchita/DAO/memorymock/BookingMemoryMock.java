package com.canchita.DAO.memorymock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

public class BookingMemoryMock implements BookingDAO {

	private static Map<Long, Booking> bookingMocks = new HashMap<Long, Booking>();
	private static long BOOKING_ID = 1;

	static {
		// Initialize an element for mocking purposes

	}

	@FactoryMethod
	public static BookingDAO getInstance() {
		return new BookingMemoryMock();
	}
	
	@Override
	public void delete(Long id) {
		bookingMocks.remove(id);

	}

	@Override
	public Booking getById(Long id) {
		return bookingMocks.get(id);
	}

	@Override
	public List<Booking> getComplexBookings(Long complexId) {

		return null;
	}

	@Override
	public List<Booking> getFieldBookings(Long fieldId) {
		return null;
	}

	@Override
	public Iterator<Booking> getFieldBookings(Long fieldId, DateTime date) {

		Collection<Booking> bookings = new ArrayList<Booking>();

		for (Booking booking : bookingMocks.values()) {
			if (booking.getSchedule().hasDay(date)) {
				bookings.add(booking);
			}
		}

		return bookings.iterator();
	}

	@Override
	public Booking save(Booking booking) throws PersistenceException {

		/*
		 * TODO esto ESTA HORRIBLE estaria increible tener la interfaz
		 * bookeableDAO y bookerDAO y que las de cancha y complejo extiendan de
		 * esas porque sino pasan estos casteos horribles
		 */

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		if (!fieldDAO.exists((Field) booking.getItem())) {
			throw new ElementNotExistsException(
					"La cancha seleccionada no existe");
		}

		if (this.viewAvailability(booking)) {
			throw new ElementExistsException("El horario ya fue reservado");
		}

		this.internalSave(BOOKING_ID, booking);

		booking.setId(BOOKING_ID++);
		
		return booking;

	}

	public boolean viewAvailability(Booking booking) {

		for (Booking otherBooking : bookingMocks.values()) {

			if (booking.inConflict(otherBooking)) {
				return true;
			}

		}

		return false;
	}

	private void internalSave(Long id, Booking booking) {
		bookingMocks.put(id, booking);
	}

	@Override
	public boolean exists(Booking booking) {

		return bookingMocks.containsValue(booking);
	}

	
	@Override
	public void cancel(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Booking> getDownBookings(Long complexId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(Booking booking) throws ElementExistsException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Booking> getAllBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> getCancelableBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean tryCancel(Booking booking, Expiration expiration) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Collection<Booking> getDownBookings(String neighbourhood,
			Long listCount) {
		// TODO Auto-generated method stub
		return null;
	}
}
