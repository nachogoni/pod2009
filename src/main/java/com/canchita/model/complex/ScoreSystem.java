package com.canchita.model.complex;

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
public class ScoreSystem {

	private Integer booking;
	private Integer deposit;
	private Integer pay;
	private Integer downBooking;
	private Integer downDeposit;

	public ScoreSystem() {
	}

	public ScoreSystem(Integer booking, Integer deposit, Integer pay,
			Integer downBooking, Integer downDeposit) {
		this.booking = booking;
		this.deposit = deposit;
		this.pay = pay;
		this.downBooking = downBooking;
		this.downDeposit = downDeposit;

	}

	public Integer getBooking() {
		return booking;
	}

	public void setBooking(Integer booking) {
		this.booking = booking;
	}

	public Integer getDeposit() {
		return deposit;
	}

	public void setDeposit(Integer deposit) {
		this.deposit = deposit;
	}

	public Integer getPay() {
		return pay;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}

	public Integer getDownBooking() {
		return downBooking;
	}

	public void setDownBooking(Integer downBooking) {
		this.downBooking = downBooking;
	}

	public Integer getDownDeposit() {
		return downDeposit;
	}

	public void setDownDeposit(Integer downDeposit) {
		this.downDeposit = downDeposit;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((booking == null) ? 0 : booking.hashCode());
		result = prime * result + ((deposit == null) ? 0 : deposit.hashCode());
		result = prime * result
				+ ((downBooking == null) ? 0 : downBooking.hashCode());
		result = prime * result
				+ ((downDeposit == null) ? 0 : downDeposit.hashCode());
		result = prime * result + ((pay == null) ? 0 : pay.hashCode());
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
		ScoreSystem other = (ScoreSystem) obj;
		if (booking == null) {
			if (other.booking != null)
				return false;
		} else if (!booking.equals(other.booking))
			return false;
		if (deposit == null) {
			if (other.deposit != null)
				return false;
		} else if (!deposit.equals(other.deposit))
			return false;
		if (downBooking == null) {
			if (other.downBooking != null)
				return false;
		} else if (!downBooking.equals(other.downBooking))
			return false;
		if (downDeposit == null) {
			if (other.downDeposit != null)
				return false;
		} else if (!downDeposit.equals(other.downDeposit))
			return false;
		if (pay == null) {
			if (other.pay != null)
				return false;
		} else if (!pay.equals(other.pay))
			return false;
		return true;
	}

	public String toString() {
		return "Reserva: " + booking + " Sena: " + deposit + " Pago: " + pay
				+ " Caida reservada: " + downBooking + " Caida senada: "
				+ downDeposit;

	}

}
