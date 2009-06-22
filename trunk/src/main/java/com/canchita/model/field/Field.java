package com.canchita.model.field;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.ExpirationDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.booking.Bookable;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Locatable;
import com.canchita.model.user.CommonUser;

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
	private Long numberOfPlayers;
	private Complex complex;
	private boolean hasRoof;
	private FloorType floor;
	private BigDecimal price;
	private boolean under_maintenance;
	private Expiration expiration;

	private BigDecimal accontationPercentage;

	/**
	 * Retorna la funcion de comparacion de las canchas por techada o no
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareMaintenance() {
		return compareMaintenance(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre cantidad de jugadores
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareMaintenance(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return (o1.isUnder_maintenance() && o2
							.isUnder_maintenance()) ? 1 : 0;
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return (o1.isUnder_maintenance() && o2.isUnder_maintenance()) ? 0
						: 1;
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por precio
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> comparePrice() {
		return comparePrice(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre precio
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> comparePrice(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getPrice().compareTo(o2.getPrice());
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o2.getPrice().compareTo(o1.getPrice());
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por tipo de piso
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareFloorType() {
		return compareFloorType(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre tipo de piso
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareFloorType(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getFloor().toString().compareTo(
							o2.getFloor().toString());
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o2.getFloor().toString().compareTo(
						o1.getFloor().toString());
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por techada o no
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareRoof() {
		return compareRoof(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre cantidad de jugadores
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareRoof(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return (o1.isHasRoof() && o2.isHasRoof()) ? 1 : 0;
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return (o1.isHasRoof() && o2.isHasRoof()) ? 0 : 1;
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por cantidad de
	 * jugadores
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareNumberOfPlayers() {
		return compareNumberOfPlayers(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre cantidad de jugadores
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareNumberOfPlayers(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getNumberOfPlayers().compareTo(
							o2.getNumberOfPlayers());
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o2.getNumberOfPlayers().compareTo(
						o1.getNumberOfPlayers());
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por descripcion
	 * ascendente
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareDescription() {
		return compareDescription(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre descripciones
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareDescription(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getDescription().compareTo(o2.getDescription());
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o2.getDescription().compareTo(o1.getDescription());
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por complejo ascendente
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareComplex() {
		return compareComplex(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre nombres de complejo
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareComplex(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getComplex().getName().compareTo(
							o2.getComplex().getName());
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o2.getComplex().getName().compareTo(
						o1.getComplex().getName());
			}
		};
	}

	/**
	 * Retorna la funcion de comparacion de las canchas por nombre ascendente
	 * 
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareNames() {
		return compareNames(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre nombres de canchas
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Field>
	 */
	public static Comparator<Field> compareNames(boolean sort) {
		if (sort) {
			return new Comparator<Field>() {
				@Override
				public int compare(Field o1, Field o2) {
					return o1.getName().compareTo(o2.getName());
				}
			};
		}
		return new Comparator<Field>() {
			@Override
			public int compare(Field o1, Field o2) {
				return o2.getName().compareTo(o1.getName());
			}
		};
	}

	public Field(long id, long complexID, String name, String description,

	long numberOfPlayers, boolean hasRoof, long floor, BigDecimal price,
			boolean under_maintenance, BigDecimal percentage) {

		this.id = id;
		this.complex = new Complex(complexID);
		this.name = name;
		this.description = description;
		this.numberOfPlayers = numberOfPlayers;
		this.hasRoof = hasRoof;
		this.floor = FloorType.values()[(int) floor];
		this.price = price;
		this.under_maintenance = under_maintenance;
		this.setAccontationPercentage(percentage);
	}

	@Override
	public String toString() {
		return "Nombre: " + this.name + " Descripción: " + this.description;
	}

	public Complex getComplex() {
		return complex;
	}

	public void setComplex(Complex complex) {
		this.complex = complex;
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
	}

	public Field(Complex complex, String name) {

		this.complex = complex;
		this.name = name;

	}

	public Field(Long fieldId) {
		this.id = fieldId;
	}

	@Override
	public Booking book(CommonUser user, Schedule hour)
			throws PersistenceException, BookingException {

		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);

		Expiration expiration = expirationDAO.getByScore(this, user.getScore());

		DateTime bookedExpiration = hour.getStartTime().minusHours(
				expiration.getBookingLimit());

		if (bookedExpiration.isBeforeNow()) {
			throw new BookingException(
					"No se pudo realizar la reserva, ya que dados sus puntos la fecha de expiración es anterior a la de la fecha");
		}

		Booking booking = new Booking(this, hour, user, bookedExpiration);

		if (!this.inAvailableHours(booking)) {
			throw new BookingException(
					"La cancha no está disponible en este horario");
		}

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		return bookingDAO.save(booking);
	}

	private boolean inAvailableHours(Booking booking) {
		return complex.inAvailableHours(booking.getSchedule());

	}

	public Iterator<Schedule> getAvailableHours(DateTime date)
			throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		Iterator<Booking> bookings = bookingDAO.getFieldBookings(this.id, date);

		Iterator<Schedule> availability = complex.getScheduleForDay(date);

		return this.getAvailableHours(date, bookings, availability);

	}

	public Iterator<Schedule> getAllHours(DateTime date) {

		List<Booking> bookings = new ArrayList<Booking>();

		Iterator<Schedule> availability = complex.getScheduleForDay(date);

		return this.getAvailableHours(date, bookings.iterator(), availability);
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

			for (int i = startHour; i < endHour; i++) {
				possibleValues.add(i);
			}
		}

		while (bookings.hasNext() && possibleValues.size() != 0) {

			int startHour, endHour;

			Schedule schedule = ((Booking) bookings.next()).getSchedule();

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

	public List<Booking> getBookings() throws PersistenceException {
		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		return bookingDAO.getFieldBookings(this.id);
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

	public boolean viewAvailability(Schedule hour) throws PersistenceException {

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);

		Booking booking = new Booking(this, hour);

		return this.complex.inAvailableHours(hour)
				&& bookingDAO.viewAvailability(booking);
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

	public void setNumberOfPlayers(Long numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public Long getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public BigDecimal getPrice() {
		return price;
	}

	public void setUnder_maintenance(boolean under_maintenance) {
		this.under_maintenance = under_maintenance;
	}

	public boolean isUnder_maintenance() {
		return under_maintenance;
	}

	@Override
	public BigDecimal getAccontationPercentage() {

		if (this.accontationPercentage == null) {
			return this.complex.getAccontationPercentage();
		}

		return accontationPercentage;
	}

	public void setAccontationPercentage(BigDecimal accontationPercentage) {
		this.accontationPercentage = accontationPercentage;
	}

}
