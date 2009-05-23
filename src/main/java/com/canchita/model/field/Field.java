package com.canchita.model.field;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
	
	public static List<Field> list (Complex complex) {
		return complex.listFields();
	}
	
	public static List<Field> list() {
		//TODO: Reemplazarlo por las llamadas a la base de datos que sean necesarias
/*
		for (Iterator<Field> i = telephones.iterator(); i.hasNext(); ) {
			this.telephones.add(i.next());
		}
		
		List<Field> fields = new ArrayList<Field>();
		
		Complex complex1 = new Complex("The Tito's Complex");
		Complex complex2 = new Complex("La casa de la nona");
		
		fields.add(new Field(complex1, "Cancha_1"));
		fields.add(new Field(complex1, "Cancha_2"));
		fields.add(new Field(complex2, "Cancha_1"));
		fields.add(new Field(complex2, "Cancha_2"));
		fields.add(new Field(complex2, "Cancha_3"));
		fields.add(new Field(complex2, "Cancha_4"));		

		return fields;*/ return null;
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
	}

	public Field(Complex complex, String name) {
		// TODO: idem arriba, rellenar con las llamadas a la DB
		if (complex.getName().equals("The titos's Complex")) {
			
			if (name.equals("Cancha_1")) {
				this.name = "Cancha_1";
				this.description = "La de adelante";
				this.complex = complex;
				this.hasRoof = true;
				this.floor = FloorType.ARTIFICIAL_GRASS;
				this.expiration = complex.getExpiration(null);
			} else if (name.equals("Cancha_2")) {
				this.name = "Cancha_2";
				this.description = "La del fondo";
				this.complex = complex;
				this.hasRoof = false;
				this.floor = FloorType.CONCRETE;
				this.expiration = complex.getExpiration(null);
			}
			
		} else if (complex.getName().equals("La casa de la nona")) {
			
			if (name.equals("Cancha_1")) {
				this.name = "Cancha_1";
				this.description = "Adelante, izquierda";
				this.complex = complex;
				this.hasRoof = true;
				this.floor = FloorType.ARTIFICIAL_GRASS;
				this.expiration = complex.getExpiration(null);
			} else if (name.equals("Cancha_2")) {
				this.name = "Cancha_2";
				this.description = "Adelante, derecha";
				this.complex = complex;
				this.hasRoof = true;
				this.floor = FloorType.CONCRETE;
				this.expiration = complex.getExpiration(null);
			} else if (name.equals("Cancha_3")) {
				this.name = "Cancha_3";
				this.description = "Atras, izquierda";
				this.complex = complex;
				this.hasRoof = false;
				this.floor = FloorType.CONCRETE;
				this.expiration = complex.getExpiration(null);
			} else if (name.equals("Cancha_4")) {
				this.name = "Cancha_4";
				this.description = "Atras, derecha";
				this.complex = complex;
				this.hasRoof = true;
				this.floor = FloorType.CONCRETE;
				this.expiration = complex.getExpiration(null);
			}
			
		}		
	}

	
	public void book(Schedule hour) {
		// TODO Auto-generated method stub

	}


	public List<Booking> getBookings() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getDescription() {
		return this.description;
	}


	public Date getExpiration(Booking booking) {
		return complex.getExpiration(booking);
	}

	public Locatable getLocation() {
		return this.complex;
	}

	
	public String getName() {
		return this.name;
	}

	
	public boolean viewAvailability(Schedule hour) {
		// TODO Auto-generated method stub
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
