package com.canchita.model.booking;

import java.math.BigDecimal;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.complex.Complex;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Locatable;
import com.canchita.model.user.CommonUser;

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

	Long getId();

	List<Booking> getBookings() throws PersistenceException;
	
	Booking book(CommonUser user, Schedule hour) throws PersistenceException, BookingException;
	
	String getName();
	
	String getDescription();
	
	Locatable getLocation();
	
	DateTime getExpiration(Booking booking);

	BigDecimal getPrice();

	BigDecimal getAccontationPercentage();
	
	Complex getComplex();
}
