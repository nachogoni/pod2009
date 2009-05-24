package com.canchita.DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.canchita.DAO.exception.ElementExistsException;
import com.canchita.model.booking.Booking;

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
	public void save(Booking booking) throws ElementExistsException {

		//TODO chequear que exista la cancha
		
		if (bookingMocks.containsValue(booking)) {
			throw new ElementExistsException(
					"This booking already exists in our records: " + booking);
		}

		this.internalSave(BOOKING_ID++,booking);
		

	}

	private void internalSave(Long id, Booking booking) {
		bookingMocks.put(id, booking);
	}

	@Override
	public boolean exists(Booking booking) {
		return bookingMocks.containsValue(booking);
	}
}
