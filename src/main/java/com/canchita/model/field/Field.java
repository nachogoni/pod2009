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
	private Long id;
	
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
	
	public Booking book(Schedule hour) {
		// TODO Auto-generated method stub
		return null;
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

	public Date getExpiration(Booking booking) {
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

}
