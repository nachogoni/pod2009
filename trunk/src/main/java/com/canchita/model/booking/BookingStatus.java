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

	BOOKED(0) {

		@Override
		public String toString() {
			
			return "Reservada";
		}
		
	},
	
	PAID(1) {

		@Override
		public String toString() {
			return "Pagada";
		}
		
	},
	
	HALF_PAID(2) {

		@Override
		public String toString() {
			return "Señada";
		}
		
	},
	
	CANCELLED(3) {

		@Override
		public String toString() {
			return "Cancelada";
		}
		
	};
	
	public static BookingStatus fromId(long id) {
		if (BookingStatus.values().length < id)
			throw new RuntimeException("BookingStatus invalid");
		
		return BookingStatus.values()[(int)id];
	}

	private Integer index;
	
	public abstract String toString();
	
	BookingStatus(int index) {
		this.index = index;
	}
	
	public Integer getIndex() {
		return index;
	}
		
}
