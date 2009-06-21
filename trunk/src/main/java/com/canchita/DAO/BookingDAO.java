package com.canchita.DAO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public interface BookingDAO {

	public Booking save(Booking booking) throws PersistenceException;

	public Booking getById(Long id) throws ElementNotExistsException;

	public void cancel(Long id);
	
	public void delete(Long id);

	public List<Booking> getComplexBookings(Long complexId);

	public List<Booking> getFieldBookings(Long fieldId);

	public Iterator<Booking> getFieldBookings(Long fieldId, DateTime date);

	public boolean viewAvailability(Booking booking);

	boolean viewAvailability(DateTime startTime, DateTime endTime);
	
	public boolean exists(Booking booking);
	// TODO ver el tema de la busqueda de reservas
	// public List<Complex> getFiltered(String name, Locatable aLocation );

	Collection<Booking> getDownBookings(Long complexId);

	public void update(Booking booking) throws ElementExistsException;

	public Collection<Booking> getDownBookings(String province, String locality, String neighbourhood, Long listCount);

	public List<Booking> getCancelableBookings();

	public boolean tryCancel(Booking booking, Expiration expiration);

	public List<Booking> getAllBookings();

	public boolean hasBookings(Long id);

	public boolean complexHasBookings(Long id);


}
