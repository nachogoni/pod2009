package com.canchita.model.complex;

import org.joda.time.DateTime;

import com.canchita.model.booking.Schedule;

public class Availability {

	Long id;
	DayOfWeek day;
	Schedule schedule;

	public Availability(Long id, long day, Schedule schedule) {
		this.id = id;
		this.day = DayOfWeek.fromId(day);
		this.schedule = schedule;
	}

	public Availability(DayOfWeek day, Schedule schedule) {
		this.day = day;
		this.schedule = schedule;
	}

	public boolean inConflict(Availability otherAvailability) {

		return this.day.equals(otherAvailability.day)
				&& this.schedule.inConflict(otherAvailability.schedule);
	}

	public boolean inDay(DateTime date) {

		return day.ordinal() == (date.getDayOfWeek() - 1);

	}

	public DayOfWeek getDay() {
		return day;
	}

	public Schedule getSchedule() {
		return schedule;
	}

	public String toString() {
		return "" + this.day + ": " + this.schedule.getStartTime() + " - "
				+ this.schedule.getEndTime();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((day == null) ? 0 : day.hashCode());
		result = prime * result
				+ ((schedule == null) ? 0 : schedule.hashCode());
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
		Availability other = (Availability) obj;
		if (day == null) {
			if (other.day != null)
				return false;
		} else if (!day.equals(other.day))
			return false;
		if (schedule == null) {
			if (other.schedule != null)
				return false;
		} else if (!schedule.equals(other.schedule))
			return false;
		return true;
	}

}
