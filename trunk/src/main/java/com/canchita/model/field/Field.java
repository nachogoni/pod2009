package com.canchita.model.field;

import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.BookingMemoryMock;
import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.PersistenceException;
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

	private Long id;
	
	private String name;
	private String description;
	private Complex complex;
	private boolean hasRoof;
	private FloorType floor;
	private Expiration expiration;
	
	public Complex getComplex() {
		return complex;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHasRoof(boolean hasRoof) {
		this.hasRoof = hasRoof;
	}

	public void setFloor(FloorType floor) {
		this.floor = floor;
	}

	public void setExpiration(Expiration expiration) {
		this.expiration = expiration;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public static List<Field> list (Complex complex) {
		return complex.listFields();
	}
	
	public static List<Field> list() {
		//TODO
		return null;
	}
	
	public Field(String name, String description, Complex complex,
			boolean hasRoof, FloorType floor, Expiration expiration) {
		super();
		this.name = name;
		this.description = description;
		this.complex = complex;
		this.hasRoof = hasRoof;
		this.floor = floor;
		this.expiration = expiration;
		this.id = -1L; //TODO: Esta bien que tenga este id? -> cuando inserto, tomo un id segun la DB
	}

	public Field(Complex complex, String name) {
		// TODO: ir a buscar al complex un determinado field		
	}
	
	public Booking book(Schedule hour) throws PersistenceException {
		
		Booking booking = new Booking(this,hour);
		
		BookingDAO bookingDAO = new BookingMemoryMock();
		
		bookingDAO.save(booking);

		return booking;
	}

	public List<Booking> getBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		return this.description;
	}
	
	public Expiration getExpiration() {
		return this.expiration;
	}

	public DateTime getExpiration(Booking booking) {
		return this.complex.getExpiration(booking);
	}

	public Locatable getLocation() {
		return this.complex;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean viewAvailability(Schedule hour) {
		// TODO A QUIEN LE PREGUNTO SI ES DISPONIBLE O NO?
		return false;
	}

	public boolean isHasRoof() {
		return hasRoof;
	}

	public FloorType getFloor() {
		return floor;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((complex == null) ? 0 : complex.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Field other = (Field) obj;
		if (complex == null) {
			if (other.complex != null)
				return false;
		} else if (!complex.equals(other.complex))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	



	
}
