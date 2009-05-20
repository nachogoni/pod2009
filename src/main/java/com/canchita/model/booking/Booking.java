package com.canchita.model.booking;

import java.util.Date;


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
public class Booking {
	
	private Bookable item;
	private BookingStatus state;
	private Schedule schedule;
	
	public void cancel() {
		
	}
	
	public void pay(Double amount) {
		
	}

	public Date getExpiration() {
		//TODO implementame
		return null;
	}

	public Bookable getItem() {
		return item;
	}

	public BookingStatus getState() {
		return state;
	}

	public Schedule getSchedule() {
		return schedule;
	}
	
	
}
