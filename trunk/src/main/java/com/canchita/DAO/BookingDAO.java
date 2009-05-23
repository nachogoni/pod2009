package com.canchita.DAO;

import java.util.Collection;

import com.canchita.DAO.exception.ElementExistsException;
import com.canchita.model.booking.Booking;

public interface BookingDAO {

	public void save(Booking booking) throws ElementExistsException;
	
	public Booking getById(Long id);
	
	public void delete(Long id);
	
	public Collection<Booking> getComplexBookings(Long complexId);

	public Collection<Booking> getFieldBookings(Long fieldId);
	
	//TODO ver el tema de la busqueda de reservas
	//public List<Complex> getFiltered(String name, Locatable aLocation );
	
}
