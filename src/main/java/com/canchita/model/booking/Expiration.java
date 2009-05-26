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
public class Expiration {

	private Integer bookingLimit;
	private Integer depositLimit;
	
	public Integer getBookingLimit() {
		return bookingLimit;
	}

	public void setBookingLimit(Integer bookingLimit) {
		this.bookingLimit = bookingLimit;
	}

	public Integer getDepositLimit() {
		return depositLimit;
	}

	public void setDepositLimit(Integer depositLimit) {
		this.depositLimit = depositLimit;
	}

	public DateTime getExpiration(Booking booking) {
		//TODO implementame
		return null;
	}
	
}
