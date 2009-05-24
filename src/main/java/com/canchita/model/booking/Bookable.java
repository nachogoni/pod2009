package com.canchita.model.booking;

import java.util.List;

import org.joda.time.DateTime;

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

	boolean viewAvailability(Schedule hour);
	
	List<Booking> getBookings();
	
	Booking book(Schedule hour);
	
	String getName();
	
	String getDescription();
	
	Locatable getLocation();
	
	DateTime getExpiration(Booking booking);
	
}
