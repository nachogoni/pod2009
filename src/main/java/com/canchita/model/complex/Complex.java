package com.canchita.model.complex;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booker;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.field.Field;
import com.canchita.model.location.Place;

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
public class Complex implements Booker {

	private String name;
	private Place place;
	private String description;
	private Calendar timeTable;
	private ScoreSystem scoreSystem;
	private List<Field> fields;
	private Expiration expiration;
	private Long id;
	
	public static List<Complex> list() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Complex(String name) {
		this.setName(name);
	}

	public List<Field> listFields() {
		// TODO Auto-generated method stub
		
		List<Field> fields = new ArrayList<Field>();

		if (this.name.equals("The titos's Complex")) {
			fields.add(new Field(this, "Cancha_1"));
			fields.add(new Field(this, "Cancha_2"));
		} else if (this.name.equals("La casa de la nona")) {
			fields.add(new Field(this, "Cancha_1"));
			fields.add(new Field(this, "Cancha_2"));
			fields.add(new Field(this, "Cancha_3"));
			fields.add(new Field(this, "Cancha_4"));
		}
		return fields;
	}
	
	
	public void add(Bookable bookable) {
		// TODO Auto-generated method stub

	}


	public Booking book(Bookable bookable, Schedule hour) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Bookable> getBookables() {
		// TODO Auto-generated method stub
		return null;
	}


	public Date getExpiration(Booking booking) {
		return expiration.getExpiration(booking);
	}


	public List<Booking> getBookings() {
		// TODO Auto-generated method stub
		return null;
	}


	public void remove(Bookable bookable) {
		// TODO Auto-generated method stub

	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Place getPlace() {
		return this.place;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return this.id;
	}
	
	public void setId(Long id){
		this.id = id;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Calendar getTimeTable() {
		return timeTable;
	}

	public void setTimeTable(Calendar timeTable) {
		this.timeTable = timeTable;
	}

	public ScoreSystem getScoreSystem() {
		return scoreSystem;
	}

	public void setScoreSystem(ScoreSystem scoreSystem) {
		this.scoreSystem = scoreSystem;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public void setExpiration(Expiration expiration) {
		this.expiration = expiration;
	}
	
	public Expiration getExpiration() {
		return expiration;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

}
