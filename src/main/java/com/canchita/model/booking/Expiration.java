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

	private Long id;
	private Integer bookingLimit;
	private Integer depositLimit;
	private Long scoreFrom;
	private Long scoreTo;

	public Expiration() {

	}

	public Expiration(long id, long scoreFrom, long scoreTo, int depositLimit,
			int bookingLimit) {
		this.id = id;
		this.scoreFrom = scoreFrom;
		this.scoreTo = scoreTo;
		this.depositLimit = depositLimit;
		this.bookingLimit = bookingLimit;

	}

	public Long getScoreFrom() {
		return scoreFrom;
	}

	public void setScoreFrom(Long scoreFrom) {
		this.scoreFrom = scoreFrom;
	}

	public Long getScoreTo() {
		return scoreTo;
	}

	public void setScoreTo(Long scoreTo) {
		this.scoreTo = scoreTo;
	}

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
		return null;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

}
