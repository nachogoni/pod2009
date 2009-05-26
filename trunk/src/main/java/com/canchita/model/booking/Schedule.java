package com.canchita.model.booking;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

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
public class Schedule {

	private DateTime startTime;
	private DateTime endTime;

	public Schedule(DateTime startTime, DateTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
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
		Schedule other = (Schedule) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	public boolean inConflict(Schedule schedule) {

		return this.contains(schedule);

	}

	public boolean contains(Schedule schedule) {
		
		if (startTime.compareTo(schedule.endTime) >= 0
				|| endTime.compareTo(schedule.startTime) <= 0) {
			return false;
		}
		
		return true;
	}

	public String toString() {
		StringBuffer ret = new StringBuffer();

		ret.append(startTime.toString("dd/MM/yyyy HH:mm")).append(" - ")
				.append(endTime.toString("dd/MM/yyyy HH:mm"));

		return ret.toString();
	}

	public boolean hasDay(DateTime date) {

		long startDay = startTime.getDayOfYear()
				+ (365 * (startTime.getYear() - 1900));
		long endDay = endTime.getDayOfYear()
				+ (365 * (endTime.getYear() - 1900));

		long dateDay = date.getDayOfYear() + (365 * (date.getYear() - 1900));

		if (startDay <= dateDay && endDay >= dateDay) {
			return true;
		}

		return false;
	}

	public DateTime getStartTime() {
		return startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public static Iterator<Schedule> createHourlySchedule(DateTime date,
			List<Integer> possibleValues) {

		List<Schedule> list = new ArrayList<Schedule>();

		for (Iterator<Integer> iterator = possibleValues.iterator(); iterator.hasNext();) {
			Integer i = iterator.next();

			DateTime from = date.withTime(i, 0, 0, 0);
			DateTime to = date.withTime(i + 1, 0, 0, 0);

			list.add(new Schedule(from, to));
		}

		return list.iterator();
	}

	public Schedule setSameDay(DateTime date) {
		
		DateTime newStart = startTime.withDate(date.getYear(), date.getMonthOfYear(), date.dayOfMonth().get());
		DateTime newEnd = endTime.withDate(date.getYear(), date.getMonthOfYear(), date.dayOfMonth().get());
		
		return new Schedule(newStart,newEnd);
	}


}
