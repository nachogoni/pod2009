package com.canchita.model.booking;

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

public enum BookingStatus {

	BOOKED(1),PAID(2),HALF_PAID(3),CANCELLED(4);
	
	public static BookingStatus fromId(long id) {
		if (BookingStatus.values().length < id)
			throw new RuntimeException("BookingStatus invalid");
		
		return BookingStatus.values()[(int)id];
	}

	private Integer index;
	
	BookingStatus(int index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
}
