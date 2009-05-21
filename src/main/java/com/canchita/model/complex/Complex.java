package com.canchita.model.complex;

import java.util.ArrayList;
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
		List<Field> fields = new ArrayList<Field>();
		List<String> tels = new ArrayList<String>();
		
		if (name.equals("The titos's Complex")) {
		
			Calendar titos_horarios = new Calendar();
			ScoreSystem titos_scores = new ScoreSystem();
			Expiration titos_expiran = new Expiration();
			
			Place titos_place = new Place("Madero 339", tels,"1233", "Pto Madero", "Buenos Aires",
					"CABA", "Argentina", "-34.030303", "+54.3434334");
			titos_place.addTelephone("4444-5555");
			titos_place.addTelephone("5555-5555");
			
			
			
			this.name = new String("The titos's Complex");
			this.place = titos_place;
			this.description = new String("El complejo mas divertido");
			this.timeTable = titos_horarios;
			this.scoreSystem = titos_scores;
			this.fields = fields;
			this.expiration = titos_expiran;

		} else if (name.equals("La casa de la nona")) {
			
			Calendar nonas_horarios = new Calendar();
			ScoreSystem nonas_scores = new ScoreSystem();
			Expiration nonas_expiran = new Expiration();		

			Place nonas_place = new Place("8 de Abril 339", tels,"1233", "Costanera", "Capital",
					"Capital", "Uruguay", "-35.030303", "+45.3434334");
			nonas_place.addTelephone("4321-1234");
			nonas_place.addTelephone("1234-4321");

			this.name = new String("La casa de la nona");
			this.place = nonas_place;
			this.description = new String("Donde te atienden mejor que en casa");
			this.timeTable = nonas_horarios;
			this.scoreSystem = nonas_scores;
			this.fields = fields;
			this.expiration = nonas_expiran;
		}
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
	
	@Override
	public void add(Bookable bookable) {
		// TODO Auto-generated method stub

	}

	@Override
	public Booking book(Bookable bookable, Schedule hour) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Bookable> getBookables() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expiration getExpiration(Booking reservation) {
		return this.expiration;
	}

	@Override
	public List<Booking> getReservations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Bookable bookable) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@Override
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

}
