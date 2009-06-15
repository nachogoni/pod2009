package com.canchita.model.rss;

import java.util.Collection;

import com.canchita.model.booking.Bookable;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;

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
	
	public static Collection<Bookable> generateDownBookings() {
		
		//TODO implementame
		return null;

	}

	public static Collection<Field> generateNewFields(String neighbourhood) 
			throws ValidationException, PersistenceException {
		Collection<Field> fields = null;
		FieldServiceProtocol fieldService = new FieldService();
		fields = fieldService.getLastFields(neighbourhood, 10L);
		
		return fields;
	}
	
	public static Collection<Field> generateNewFields() 
			throws ValidationException, PersistenceException {
		return generateNewFields(null);
	}
	
}
