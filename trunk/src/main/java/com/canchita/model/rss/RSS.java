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
	
	public static Collection<Booking> generateDownBookings(String province, String locality, String neighbourhood)
			throws ValidationException, PersistenceException {
		
		return new BookingService().getDownBookings(province, locality, neighbourhood, 10L);
	}

	public static Collection<Field> generateNewFields(String province, String locality, String neighbourhood) 
			throws ValidationException, PersistenceException {
		
		return new FieldService().getLastFields(province, locality, neighbourhood, 10L);
	}
	
}
