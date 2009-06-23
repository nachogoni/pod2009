package com.canchita.model.booking;

import java.math.BigDecimal;
import java.util.Comparator;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.ExpirationDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.UserException;
import com.canchita.model.field.Field;
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
public class Booking {

	private Long id;
	private Bookable item;
	private CommonUser owner;
	private BookingStatus state;
	private Schedule schedule;
	private BigDecimal cost;
	private BigDecimal paid;
	private DateTime expirationDate;
	
	 /**
	 * Retorna la funcion de comparacion de las reservas por pago
	 * 
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> comparePays() {
		return comparePays(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre reservas por pago
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> comparePays(boolean sort) {
		if (sort) {
			return new Comparator<Booking>() {
				@Override
				public int compare(Booking o1, Booking o2) {
					return o1.paid.compareTo(o2.paid);
				}
			};
		}
		return new Comparator<Booking>() {
			@Override
			public int compare(Booking o2, Booking o1) {
				return o1.paid.compareTo(o2.paid);
			}
		};
	}
	
	/**
	 * Retorna la funcion de comparacion de las reservas por costo
	 * 
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareCosts() {
		return compareCosts(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre reservas por costo
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareCosts(boolean sort) {
		if (sort) {
			return new Comparator<Booking>() {
				@Override
				public int compare(Booking o1, Booking o2) {
					return o1.cost.compareTo(o2.cost);
				}
			};
		}
		return new Comparator<Booking>() {
			@Override
			public int compare(Booking o2, Booking o1) {
				return o1.cost.compareTo(o2.cost);
			}
		};
	}
	
	/**
	 * Retorna la funcion de comparacion de las reservas por estado
	 * 
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareStates() {
		return compareStates(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre reservas por estado
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareStates(boolean sort) {
		if (sort) {
			return new Comparator<Booking>() {
				@Override
				public int compare(Booking o1, Booking o2) {
					return o1.state.toString().compareTo(o2.state.toString());
				}
			};
		}
		return new Comparator<Booking>() {
			@Override
			public int compare(Booking o2, Booking o1) {
				return o1.state.toString().compareTo(o2.state.toString());
			}
		};
	}
	
	/**
	 * Retorna la funcion de comparacion de las reservas por complejo
	 * 
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareComplexs() {
		return compareComplexs(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre reservas por complejo
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareComplexs(boolean sort) {
		if (sort) {
			return new Comparator<Booking>() {
				@Override
				public int compare(Booking o1, Booking o2) {
					return o1.item.getComplex().getName().compareTo(o2.item.getComplex().getName());
				}
			};
		}
		return new Comparator<Booking>() {
			@Override
			public int compare(Booking o2, Booking o1) {
				return o1.item.getComplex().getName().compareTo(o2.item.getComplex().getName());
			}
		};
	}
	
	/**
	 * Retorna la funcion de comparacion de las reservas por cancha
	 * 
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareFields() {
		return compareFields(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre reservas por cancha
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareFields(boolean sort) {
		if (sort) {
			return new Comparator<Booking>() {
				@Override
				public int compare(Booking o1, Booking o2) {
					return o1.item.getName().compareTo(o2.item.getName());
				}
			};
		}
		return new Comparator<Booking>() {
			@Override
			public int compare(Booking o2, Booking o1) {
				return o1.item.getName().compareTo(o2.item.getName());
			}
		};
	}

	
	/**
	 * Retorna la funcion de comparacion de las reservas por nombre
	 * 
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareUserNames() {
		return compareUserNames(true);
	}

	/**
	 * Retorna la funcion de comparacion sobre reservas por nombre
	 * 
	 * @param sort
	 *            si es true devuelve ascendente, sino descendente
	 * @return Comparator<Booking>
	 */
	public static Comparator<Booking> compareUserNames(boolean sort) {
		if (sort) {
			return new Comparator<Booking>() {
				@Override
				public int compare(Booking o1, Booking o2) {
					return o1.owner.getUsername().compareTo(o2.owner.getUsername());
				}
			};
		}
		return new Comparator<Booking>() {
			@Override
			public int compare(Booking o2, Booking o1) {
				return o1.owner.getUsername().compareTo(o2.owner.getUsername());
			}
		};
	}
	
	public Booking(Long id) {
		this.setId(id);
	}

	public Booking(Long id, Bookable item, CommonUser owner, long state,
			Schedule schedule, BigDecimal cost, BigDecimal paid, DateTime expirationDate) {
		this.id = id;
		this.item = item;
		this.owner = owner;
		this.state = BookingStatus.fromId(state);
		this.schedule = schedule;
		this.cost = cost;
		this.paid = paid;
		this.expirationDate = expirationDate;
	}

	public Booking(Bookable item, Schedule schedule, CommonUser owner, DateTime expirationDate) {
		
		this(item,schedule);	
		this.owner = owner;
		this.expirationDate = expirationDate;
	}

	public Booking(Bookable item, Schedule schedule) {
		this.item = item;
		this.schedule = schedule;
		this.state = BookingStatus.BOOKED;
		this.cost = item.getPrice();
		this.paid = new BigDecimal("0.00");
	}
	
	public void cancel() throws BookingException {
		
		BookingDAO bookingDAO;
		try {
			bookingDAO = DAOFactory.get(DAO.BOOKING);
		} catch (PersistenceException e) {
			throw new BookingException("No se pudo cancelar la reserva");
		}
		
		bookingDAO.cancel(this.getId());
		
	}

	public boolean pay(BigDecimal amount, ScoreSystem scoreSystem) throws BookingException {
		
		int score = 0;
		boolean exceeded = false;
		
		if( this.state.equals(BookingStatus.PAID) ) {
			throw new BookingException("La reserva ya está paga");
		}
		
		if( this.state.equals(BookingStatus.CANCELLED) ) {
			throw new BookingException("La reserva fue cancelada");
		}
		
		/*
		 * If amount exceeds cost, then we change it 
		 * so when we add it to the paid amount it
		 * gives the cost
		 */
		if( amount.add(paid).compareTo(cost) > 0 ) {
		 	amount = cost.subtract(paid);
		 	exceeded = true;
		}
		
		paid = paid.add(amount);
		
		BigDecimal percentage = this.getItem().getAccontationPercentage();
		
		if( this.state.equals(BookingStatus.BOOKED) && paid.compareTo(cost.multiply(percentage)) > 0 ) {
			this.state = BookingStatus.HALF_PAID;
			
			ExpirationDAO expirationDAO;
			Expiration expiration;
			
			try {
				expirationDAO = DAOFactory.get(DAO.EXPIRATION);
				expiration = expirationDAO.getByScore((Field)this.getItem(), this.owner.getScore());
			} catch (PersistenceException e) {
				throw new BookingException("No se pudo cargar el pago de la reserva");
			}

			expirationDate = this.schedule.getStartTime().minusHours(expiration.getDepositLimit());
			
			score += scoreSystem.getDeposit();
		}
		
		if( this.state.equals(BookingStatus.HALF_PAID) && paid.compareTo(cost) == 0 ) {
			this.state = BookingStatus.PAID;
			expirationDate = null;
			
			score += scoreSystem.getPay();
		}
		
		try {
			BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);
			bookingDAO.update(this);
			
			if( score != 0 ) {
				this.getOwner().addScore(score);
			}
			
		} catch (PersistenceException e) {
			throw new BookingException("No se pudo cargar el pago de la reserva",e);
		}
		catch (UserException e) {
			throw new BookingException("No se pudieron cargar los puntos del usuario",e);
		}
		
		return exceeded;
	}

	public DateTime getExpiration() {
		return item.getExpiration(this);
	}

	public Bookable getItem() {
		return item;
	}

	public BookingStatus getState() {
		return state;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result
				+ ((schedule == null) ? 0 : schedule.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
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
		Booking other = (Booking) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		return true;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean inConflict(Booking otherBooking) {

		return schedule.inConflict(otherBooking.schedule);
	}

	@Override
	public String toString() {

		StringBuffer ret = new StringBuffer();

		ret.append(item).append(" : ").append(schedule);

		return ret.toString();

	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setOwner(CommonUser owner) {
		this.owner = owner;
	}

	public CommonUser getOwner() {
		return owner;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public BigDecimal getPaid() {
		return paid;
	}

	public DateTime getExpirationDate() {
		return expirationDate;
	}
	
	
	

}
