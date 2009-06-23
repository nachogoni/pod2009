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
		
	}

	@Override
	public Collection<Booking> getDownBookings(Long complexId) {
		return null;
	}

	@Override
	public void update(Booking booking) throws ElementExistsException {
		
	}

	@Override
	public List<Booking> getAllBookings() {
		return null;
	}

	@Override
	public List<Booking> getCancelableBookings() {
		return null;
	}

	@Override
	public boolean tryCancel(Booking booking, Expiration expiration) {
		return false;
	}

	@Override
	public Collection<Booking> getDownBookings(String province,
			String locality, String neighbourhood, Long listCount) {
		return null;
	}

	@Override
	public boolean complexHasBookings(Long id) {
		return false;
	}

	@Override
	public boolean hasBookings(Long id) {
		return false;
	}

	@Override
	public boolean viewAvailability(DateTime startTime, DateTime endTime) {
		return false;
	}
}
