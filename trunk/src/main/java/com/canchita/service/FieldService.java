package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.memorymock.ComplexMemoryMock;
import com.canchita.DAO.memorymock.FieldMemoryMock;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public class FieldService implements FieldServiceProtocol {

	public void deleteField(Long id) throws ElementNotExistsException {
		(new FieldMemoryMock()).delete(id);
	}

	public Collection<Field> listField() {
		Collection<Field> fields = (new FieldMemoryMock()).getAll();
		return fields;
	}

	public Collection<Field> listField(Long idComplex)
			throws ValidationException, ElementNotExistsException {

		FieldDAO fieldDAO = new FieldMemoryMock();

		return fieldDAO.getFiltered(idComplex);

	}

	public Collection<Field> listField(String filter)
			throws ValidationException {

		Validator validator = new IsAlphaNum(true);

		if (!validator.validate(filter)) {
			throw new ValidationException(
					"Error en el criterio de búsqueda, el mismo debe ser alfanumérico");
		}

		FieldDAO fieldDAO = new FieldMemoryMock();

		return fieldDAO.getFiltered(filter);

	}

	public Long saveField(String name, String description, Long idComplex,
			Boolean hasRoof, FloorType floor) throws PersistenceException {

		Field aField = new Field((new ComplexMemoryMock()).getById(idComplex),
				name);

		aField.setDescription(description);
		aField.setHasRoof(hasRoof);
		aField.setFloor(floor);

		(new FieldMemoryMock()).save(aField);

		return aField.getId();

	}

	public void updateField(Long id, String name, String description,
			Boolean hasRoof, FloorType floor) throws PersistenceException {

		Field aField = getById(id);

		if (name != null) {
			aField.setName(name);
		}
		if (description != null) {
			aField.setDescription(description);
		}
		if (hasRoof != null) {
			aField.setHasRoof(hasRoof);
		}
		if (floor != null) {
			aField.setFloor(floor);
		}

		(new FieldMemoryMock()).update(aField);
	}

	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException {

		ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
				downBooking, downDeposit);
		try {
			Field aField = getById(id);
			aField.setScoreSystem(scoreSystem);
			(new FieldMemoryMock()).update(aField);
		} catch (PersistenceException e) {
			throw e;
		}

	}

	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException {
		Expiration anExpiration = new Expiration();

		if (bookingLimit == null || bookingLimit < 0 || depositLimit == null
				|| depositLimit < 0)
			throw new IllegalArgumentException("Valores de caducidad invalidos");

		if (depositLimit < bookingLimit) {
			throw new IllegalArgumentException(
					"El valor de caducidad de seña no puede ser menor al valor de caducidad de la reserva");
		}

		anExpiration.setBookingLimit(bookingLimit);
		anExpiration.setDepositLimit(depositLimit);

		try {
			FieldMemoryMock fieldPersistor = new FieldMemoryMock();
			Field aField = fieldPersistor.getById(id);
			aField.setExpiration(anExpiration);
			fieldPersistor.update(aField);
		} catch (Exception e) {
			throw new PersistenceException("Cancha no encontrado");
		}

	}

	@Override
	public Iterator<Schedule> getAvailableHours(Long id, DateTime date)
			throws ElementNotExistsException {

		FieldDAO fieldDAO = new FieldMemoryMock();

		Field field = fieldDAO.getById(id);

		return field.getAvailableHours(date);

	}

	@Override
	public Field getById(Long id) throws ElementNotExistsException {
		return (new FieldMemoryMock()).getById(id);
	}

	@Override
	public Iterator<Booking> getBookings(Long fieldId)
			throws ElementNotExistsException {

		FieldDAO fieldDAO = new FieldMemoryMock();

		Field field = fieldDAO.getById(fieldId);

		return field.getBookings();
	}

	@Override
	public Long getComplexId(Long id) throws ElementNotExistsException {

		FieldDAO fieldDAO = new FieldMemoryMock();

		Field field = fieldDAO.getById(id);

		return field.getComplex().getId();
	}
	
	@Override
	public void addTimeTable(Long id, DateTime startMon, DateTime endMon,
			DateTime startTues, DateTime endTues, DateTime startWed,
			DateTime endWed, DateTime startThurs, DateTime endThurs,
			DateTime startFri, DateTime endFri, DateTime startSat,
			DateTime endSat, DateTime startSun, DateTime endSun)
			throws InvalidScheduleException, PersistenceException {

		if (startMon == null || endMon == null || startMon.isAfter(endMon)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Lunes incorrectas"));
		}
		if (startTues == null || endTues == null || startTues.isAfter(endTues)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Martes incorrectas"));
		}
		if (startThurs == null || endThurs == null
				|| startThurs.isAfter(endThurs)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Jueves incorrectas"));
		}
		if (startWed == null || endWed == null || startWed.isAfter(endWed)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Miercoles incorrectas"));
		}
		if (startFri == null || endFri == null || startFri.isAfter(endFri)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Viernes incorrectas"));
		}
		if (startSat == null || endSat == null || startSat.isAfter(endSat)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Sabado incorrectas"));
		}
		if (startSun == null || endSun == null || startSun.isAfter(endSun)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para dia Domingo incorrectas"));
		}

		Calendar aCalendar = new Calendar();

		Schedule monSchedule = new Schedule(startMon, endMon);
		Availability monAvailability = new Availability(DayOfWeek.MONDAY,
				monSchedule);
		aCalendar.add(monAvailability);

		Schedule tuesSchedule = new Schedule(startTues, endTues);
		Availability tuesAvailability = new Availability(DayOfWeek.TUESDAY,
				tuesSchedule);
		aCalendar.add(tuesAvailability);

		Schedule wedSchedule = new Schedule(startWed, endWed);
		Availability wedAvailability = new Availability(DayOfWeek.WEDNESDAY,
				wedSchedule);
		aCalendar.add(wedAvailability);

		Schedule thursSchedule = new Schedule(startThurs, endThurs);
		Availability thursAvailability = new Availability(DayOfWeek.THURSDAY,
				thursSchedule);
		aCalendar.add(thursAvailability);

		Schedule friSchedule = new Schedule(startFri, endFri);
		Availability friAvailability = new Availability(DayOfWeek.FRIDAY,
				friSchedule);
		aCalendar.add(friAvailability);

		Schedule satSchedule = new Schedule(startSat, endSat);
		Availability satAvailability = new Availability(DayOfWeek.SATURDAY,
				satSchedule);
		aCalendar.add(satAvailability);

		Schedule sunSchedule = new Schedule(startSun, endSun);
		Availability sunAvailability = new Availability(DayOfWeek.SUNDAY,
				sunSchedule);
		aCalendar.add(sunAvailability);

		try {
			FieldMemoryMock fieldPersistor = new FieldMemoryMock();
			Field aField = fieldPersistor.getById(id);
			aField.setTimeTable(aCalendar);
			fieldPersistor.update(aField);
		} catch (Exception e) {
			throw new PersistenceException("Complejo no encontrado");
		}
	}

}
