package com.canchita.model.complex;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

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
	private List<Field> fields;
	private Expiration expiration;
	private Long id;
	private String email;
	private Blob picture;
	private String fax;
	protected List<String> phones;

	public static List<Complex> list() {
		// TODO Auto-generated method stub
		return null;
	}

	public Complex(String name) {
		this.setName(name);
		this.phones = new LinkedList<String>();
	}

	public Complex(Integer id, String name, String description, String address,
			String city, String state, String zipCode, String country,
			String fax, String email, Blob picture, String latitude,
			String longitude) {
		this.id = new Long(id);
		this.name = name;
		this.description = description;
		this.place = (new Place.Builder(address, city).country(country)
				.latitude(latitude).longitude(longitude).town(city)
				.state(state).zipCode(zipCode)).build();
		this.email = email;
		this.fax = fax;
		this.setPicture(picture);
		this.phones = new LinkedList<String>();

	}

	public Complex(long complexID) {
		this.id = complexID;
		this.phones = new LinkedList<String>();
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Field> listFields() {
		// TODO Auto-generated method stub
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

	public DateTime getExpiration(Booking booking) {
		return expiration.getExpiration(booking);
	}

	public List<Booking> getBookings() {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(Bookable bookable) {
		// TODO Auto-generated method stub

	}

	public Iterator<Schedule> getScheduleForDay(DateTime date) {

		return this.timeTable.getScheduleForDay(date);

	}

	public boolean inAvailableHours(Schedule schedule) {

		DateTime startTime = schedule.getStartTime();
		DateTime endTime = schedule.getEndTime();
		Collection<Schedule> collection = new ArrayList<Schedule>();

		long startDay = startTime.getDayOfYear() + (365 * startTime.getYear());
		long endDay = endTime.getDayOfYear() + (365 * endTime.getYear());

		long diffDays = endDay - startDay;

		/*
		 * We get the schedule for every day in the schedule parameter
		 */
		for (int i = 0; i <= diffDays; i++) {

			DateTime day = startTime.plusDays(i);

			Iterator<Schedule> iterator = this.getScheduleForDay(day);

			while (iterator.hasNext()) {
				Schedule aSchedule = (Schedule) iterator.next();
				collection.add(aSchedule);
			}

		}

		return this.inAvailableHours(collection, schedule);

	}

	private boolean inAvailableHours(Collection<Schedule> collection,
			Schedule schedule) {

		for (Schedule otherSchedule : collection) {

			if (otherSchedule.contains(schedule)) {
				return true;
			}
		}

		return false;

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

	public void setId(Long id) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((place == null) ? 0 : place.hashCode());
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
		Complex other = (Complex) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (place == null) {
			if (other.place != null)
				return false;
		} else if (!place.equals(other.place))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nombre: " + name + " Descripción: " + description + " Email: "
				+ email;
	}

	public void setPicture(Blob picture) {
		this.picture = picture;
	}

	public Blob getPicture() {
		return picture;
	}

	public void setPhone(String phone) {
		this.phones.add(phone);

	}

	public List<String> getPhones() {
		return phones;
	}

}
