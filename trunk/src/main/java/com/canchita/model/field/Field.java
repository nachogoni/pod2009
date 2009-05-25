package com.canchita.model.field;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.BookingMemoryMock;
import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.ComplexMemoryMock;
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

	public static List<Field> list(Complex complex) {
		return complex.listFields();
	}

	public static List<Field> list() {
		// TODO
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
		this.id = -1L; // TODO: Esta bien que tenga este id? -> cuando inserto,
		// tomo un id segun la DB
	}

	public Field(Complex complex, String name) {
		// TODO: ir a buscar al complex un determinado field
	}

	public Booking book(Schedule hour) throws PersistenceException {

		Booking booking = new Booking(this, hour);

		BookingDAO bookingDAO = new BookingMemoryMock();

		bookingDAO.save(booking);

		return booking;
	}

	public Iterator<Schedule> getAvailableHours(DateTime date) {

		BookingDAO bookingDAO = new BookingMemoryMock();

		Iterator<Booking> bookings = bookingDAO.getFieldBookings(this.id, date);

		Iterator<Schedule> availability = complex.getScheduleForDay(date);

		return this.getAvailableHours(date, bookings, availability);

	}

	private Iterator<Schedule> getAvailableHours(DateTime date,
			Iterator<Booking> bookings, Iterator<Schedule> availability) {

		List<Integer> possibleValues = new LinkedList<Integer>();

		/*
		 * TODO it assumes that we can only book a field for 1 hour. See what to
		 * do if this requisite changes
		 */

		while (availability.hasNext()) {
			Schedule schedule = (Schedule) availability.next();

			int startHour = schedule.getStartTime().getHourOfDay();
			int endHour = schedule.getEndTime().getHourOfDay();

			System.out.println("Horario de atencion: " + startHour + " "
					+ endHour);

			for (int i = startHour; i < endHour; i++) {
				possibleValues.add(i);
			}
		}

		System.out.println("Vienen los bookings");

		while (bookings.hasNext() && possibleValues.size() != 0) {

			int startHour, endHour;

			Schedule schedule = ((Booking) bookings.next()).getSchedule();
			System.out.println(schedule);

			// Bookings could be for various days or even years
			// leap years are not taken into account

			long startDay = schedule.getStartTime().getDayOfYear()
					+ (365 * (schedule.getStartTime().getYear() - 1900));
			long endDay = schedule.getEndTime().getDayOfYear()
					+ (365 * (schedule.getEndTime().getYear() - 1900));

			long day = date.getDayOfYear() + (365 * (date.getYear() - 1900));

			/*
			 * If booking was does not
			 */

			/*
			 * Booking takes the whole day...we are done
			 */
			if (startDay < day && endDay > day) {
				possibleValues = new LinkedList<Integer>();
				break;
			}

			if (startDay < day && endDay == day) {
				startHour = 0;
				endHour = schedule.getEndTime().getHourOfDay();
			} else if (startDay == day && endDay > day) {
				startHour = schedule.getStartTime().getHourOfDay();
				endHour = 24;
			} else {
				startHour = schedule.getStartTime().getHourOfDay();
				endHour = schedule.getEndTime().getHourOfDay();
			}

			for (int i = startHour; i < endHour; i++) {
				possibleValues.remove(Integer.valueOf(i));
			}
		}

		Collections.sort(possibleValues);

		return Schedule.createHourlySchedule(date, possibleValues);
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
