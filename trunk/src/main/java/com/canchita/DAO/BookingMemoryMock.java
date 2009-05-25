package com.canchita.DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.canchita.model.booking.Booking;
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

	@Override
	public void delete(Long id) {
		bookingMocks.remove(id);

	}

	@Override
	public Booking getById(Long id) {
		return bookingMocks.get(id);
	}

	@Override
	public Collection<Booking> getComplexBookings(Long complexId) {

		return null;
	}

	@Override
	public Collection<Booking> getFieldBookings(Long fieldId) {
		return null;
	}

	@Override
	public void save(Booking booking) throws PersistenceException {

		/*
		 * TODO esto ESTA HORRIBLE estaria increible tener
		 * la interfaz bookeableDAO y bookerDAO y que las de cancha
		 * y complejo extiendan de esas porque sino pasan estos
		 * casteos horribles
		 */
		
		FieldDAO fieldDAO = new FieldMemoryMock();
		
		if( ! fieldDAO.exists((Field) booking.getItem()) ) {
			throw new ElementNotExistsException("La cancha seleccionada no existe");
		}

		if (this.viewAvailability(booking)) {
			throw new ElementExistsException("El horario ya fue reservado");
		}

		this.internalSave(BOOKING_ID, booking);

		booking.setId(BOOKING_ID++);
		
	}

	public boolean viewAvailability(Booking booking) {

		for (Booking otherBooking : bookingMocks.values()) {
			
			if( booking.inConflict(otherBooking) ){
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
}
