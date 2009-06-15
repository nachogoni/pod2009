package com.canchita.model.rss;

import java.util.Collection;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.service.BookingService;
import com.canchita.service.FieldService;

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

public class RSS {

	public static Collection<String> generateNews() {
		//TODO implementame
		return null;
	}
	
	public static Collection<Booking> generateDownBookings()
			throws ValidationException, PersistenceException {
		
		return new BookingService().getDownBookings();

	}

	public static Collection<Field> generateNewFields(String neighbourhood) 
			throws ValidationException, PersistenceException {
		
		return new FieldService().getLastFields(neighbourhood, 10L);
	}
	
	public static Collection<Field> generateNewFields() 
			throws ValidationException, PersistenceException {
		return generateNewFields(null);
	}
	
}
