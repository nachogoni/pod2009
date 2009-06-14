package com.canchita.DAO;

import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public interface BookingDAO {

	public Booking save(Booking booking) throws PersistenceException;

	public Booking getById(Long id) throws ElementNotExistsException;

	public void delete(Long id);

	public Iterator<Booking> getComplexBookings(Long complexId);

	public Iterator<Booking> getFieldBookings(Long fieldId);

	public Iterator<Booking> getFieldBookings(Long fieldId, DateTime date);

	public boolean viewAvailability(Booking booking);
	
	public boolean exists(Booking booking);
	// TODO ver el tema de la busqueda de reservas
	// public List<Complex> getFiltered(String name, Locatable aLocation );

}
