package com.canchita.model.booking;

import org.joda.time.DateTime;



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
		
	public Booking(Long id) {
		this.id = id;
	}
	
	public Booking(Bookable item, Schedule schedule) {
		
	}
	
	public void cancel() {
		
	}
	
	public void pay(Double amount) {
		
	}

	public DateTime getExpiration() {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Booking other = (Booking) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean inConflict(Booking otherBooking) {
		
		return schedule.inConflict(otherBooking.schedule);
	}
	
	
	
}
