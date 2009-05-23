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
	
	private Long id;
	private Bookable item;
	private BookingStatus state;
	private Schedule schedule;
	
	public void cancel() {
		
	}
	
	public void pay(Double amount) {
		
	}

	public Date getExpiration() {
		return item.getExpiration(this);
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
