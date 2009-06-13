package com.canchita.model.complex;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Schedule;

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

public class Calendar {

	private List<Availability> availabilities;

	public Calendar() {

		this.availabilities = new LinkedList<Availability>();
	}

	public List<Availability> getAvailabilities() {
		return availabilities;
	}

	public void add(Availability availability) {

		if (this.inConflict(availability)) {
			throw new IllegalArgumentException(
					"Esta disponibilidad se pisa con otra");
		}

		this.availabilities.add(availability);

	}

	public void remove(Availability availability) {
		this.availabilities.remove(availability);
	}

	private boolean inConflict(Availability availability) {

		for (Availability otherAvailability : this.availabilities) {

			if (availability.inConflict(otherAvailability)) {
				return true;
			}
		}

		return false;
	}

	@Override
	public String toString(){
		String ret = "";
		for (Availability av : this.availabilities) {
			ret += av + "\n";
			
		}

		return ret;
	}

	/*
	 * TODO esto ESTA MAL!!!! porque los horarios pueden estar en distinto orden
	 * hay que hacer que el equals apra cada uno de uno se barra toda la lista
	 * del otro buscando el elemento. Con hashCode se puede hacer mas sencillo
	 * que es sumar todos los hashcodes el tema es que va a ser una peor
	 * implementacion.
	 */

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((availabilities == null) ? 0 : availabilities.hashCode());
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
		Calendar other = (Calendar) obj;
		if (availabilities == null) {
			if (other.availabilities != null)
				return false;
		} else if (!availabilities.equals(other.availabilities))
			return false;
		return true;
	}

	public Iterator<Schedule> getScheduleForDay(DateTime date) {

		Collection<Schedule> collection = new ArrayList<Schedule>();

		for (Availability availability : availabilities) {

			if (availability.inDay(date)) {

				/*
				 * As the schedule was added with a random day, we adjust it
				 */

				Schedule schedule = availability.getSchedule();

				collection.add(schedule.setSameDay(date));
			}

		}

		return collection.iterator();
	}

}
