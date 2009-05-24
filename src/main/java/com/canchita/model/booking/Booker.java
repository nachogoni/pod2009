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
public interface Booker extends Locatable {

	Booking book(Bookable bookable, Schedule hour);
	
	List<Booking> getBookings();
	
	List<Bookable> getBookables();
	
	void add(Bookable bookable);
	
	void remove(Bookable bookable);
	
	DateTime getExpiration(Booking booking);
	
}
