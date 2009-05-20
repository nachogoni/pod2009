package com.canchita.model.field;

import java.util.Date;
import java.util.List;

import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Complex;
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

public class Field implements Bookable {

	private String name;
	private String description;
	private Complex complex;
	private boolean hasRoof;
	private FloorType floor;
	private Expiration expiration;
	
	public static List<Field> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void book(Schedule hour) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Booking> getBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getExpiration(Booking booking) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locatable getLocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean viewAvailability(Schedule hour) {
		// TODO Auto-generated method stub
		return false;
	}

}
