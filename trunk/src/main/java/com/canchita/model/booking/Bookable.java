package com.canchita.model.booking;

import java.util.Iterator;
import org.joda.time.DateTime;

import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Locatable;

/**
 * 
 * @author Pablo Federico Abramowicz
 * @author Martín Esteban Cabral
 * @author Darío Maximiliano Gomez Vidal
 * @author Juan Ignacio Goñi
 * @author Martín Palombo
 * @author Carlos Manuel Sessa
 *
 */
public interface Bookable {

	boolean viewAvailability(Schedule hour) throws PersistenceException;
	
	Iterator<Booking> getBookings() throws PersistenceException;
	
	Booking book(Schedule hour) throws PersistenceException, BookingException;
	
	String getName();
	
	String getDescription();
	
	Locatable getLocation();
	
	DateTime getExpiration(Booking booking);
	
}
