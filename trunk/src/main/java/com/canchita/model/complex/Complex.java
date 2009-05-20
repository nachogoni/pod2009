package com.canchita.model.complex;

import java.util.Date;
import java.util.List;

import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booker;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.field.Field;
import com.canchita.model.location.Place;

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
public class Complex implements Booker {

	String name;
	Place place;
	String description;
	Calendar timeTable;
	ScoreSystem scoreSystem;
	List<Field> fields;
	Expiration expiration;
	
	public static List<Complex> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Field> listFields() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void add(Bookable bookable) {
		// TODO Auto-generated method stub

	}

	@Override
	public Booking book(Bookable bookable, Schedule hour) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bookable> getBookables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getExpiration(Booking reservation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Booking> getReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Bookable bookable) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Place getPlace() {
		// TODO Auto-generated method stub
		return null;
	}

}
