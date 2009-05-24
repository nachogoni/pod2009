package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.PersistenceException;

public interface BookingDAO {

	public void save(Booking booking) throws PersistenceException;
	
	public Booking getById(Long id);
	
	public void delete(Long id);
	
	public Collection<Booking> getComplexBookings(Long complexId);

	public Collection<Booking> getFieldBookings(Long fieldId);
	
	public boolean exists(Booking booking);
	//TODO ver el tema de la busqueda de reservas
	//public List<Complex> getFiltered(String name, Locatable aLocation );
	
}
