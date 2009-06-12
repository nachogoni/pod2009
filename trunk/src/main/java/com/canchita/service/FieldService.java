package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.IsAlphaNumS;
import com.canchita.helper.validator.IsNumeric;
import com.canchita.helper.validator.Validator;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public class FieldService implements FieldServiceProtocol {

	public void deleteField(Long id) throws PersistenceException {

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		fieldDAO.delete(id);
	}

	public Collection<Field> listField() throws PersistenceException {
		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		Collection<Field> fields = fieldDAO.getAll();
		return fields;
	}

	public Collection<Field> listField(Long idComplex)
			throws ValidationException, PersistenceException {

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		return fieldDAO.getByComplex(idComplex);

	}

	public Collection<Field> listField(String filter)
			throws ValidationException, PersistenceException {

		Validator validator = new IsAlphaNum(true);

		if (!validator.validate(filter)) {
			throw new ValidationException(
					"Error en el criterio de búsqueda, el mismo debe ser alfanumérico");
		}

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		return fieldDAO.getFiltered(filter);

	}

	@Override
	public Collection<Field> listField(String searchName,
			String searchDescription, String searchMaxPrice,
			String searchNumberOfPlayers, String searchHasRoof,
			String searchFloorType) throws ValidationException,
			PersistenceException {

		Validator alnumValidator = new IsAlphaNum(true);
		Validator numValidator = new IsNumeric();

		if ((searchName != null && !alnumValidator.validate(searchName))
				|| (searchDescription != null && !alnumValidator
						.validate(searchDescription))
				|| (searchMaxPrice != null && !numValidator
						.validate(searchMaxPrice))
				|| (searchNumberOfPlayers != null && !numValidator
						.validate(searchNumberOfPlayers))
				|| (searchFloorType != null && !numValidator
						.validate(searchFloorType))) {
			throw new ValidationException("Error en el criterio de búsqueda");
		}

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		return fieldDAO.getFiltered(searchName, searchDescription,
				searchMaxPrice, searchNumberOfPlayers, searchHasRoof,
				searchFloorType);
	}

	@Deprecated
	public Long saveField(String name, String description, Long idComplex,
			Boolean hasRoof, FloorType floor) throws PersistenceException {

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		Field aField = new Field(complexDAO.getById(idComplex), name);

		aField.setDescription(description);
		aField.setHasRoof(hasRoof);
		aField.setFloor(floor);

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		fieldDAO.save(aField);

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

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		fieldDAO.update(aField);
	}

	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException {

		ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
				downBooking, downDeposit);
		try {
			Field aField = getById(id);
			aField.setScoreSystem(scoreSystem);

			FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

			fieldDAO.update(aField);
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
			FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

			Field aField = fieldDAO.getById(id);
			aField.setExpiration(anExpiration);
			fieldDAO.update(aField);
		} catch (Exception e) {
			throw new PersistenceException("Cancha no encontrado");
		}

	}

	@Override
	public Iterator<Schedule> getAvailableHours(Long id, DateTime date)
			throws PersistenceException {

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		Field field = fieldDAO.getById(id);

		return field.getAvailableHours(date);

	}

	@Override
	public Field getById(Long id) throws PersistenceException {

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		return fieldDAO.getById(id);
	}

	@Override
	public Iterator<Booking> getBookings(Long fieldId)
			throws PersistenceException {

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		Field field = fieldDAO.getById(fieldId);

		return field.getBookings();
	}

	@Override
	public Long getComplexId(Long id) throws PersistenceException {

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

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
			FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

			Field aField = fieldDAO.getById(id);
			aField.setTimeTable(aCalendar);
			fieldDAO.update(aField);
		} catch (Exception e) {
			throw new PersistenceException("Complejo no encontrado");
		}
	}

	public static class FieldBuilder {
		static Field aField = null;

		public static void Build(String name, String description,
				Long idComplex, Boolean hasRoof, FloorType floor)
				throws PersistenceException {

			ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

			aField = new Field(complexDAO.getById(idComplex), name);

			aField.setDescription(description);
			aField.setHasRoof(hasRoof);
			aField.setFloor(floor);

			FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

			fieldDAO.save(aField);

		}

		public static void addScoreSystem(Integer booking, Integer deposit,
				Integer pay, Integer downBooking, Integer downDeposit)
				throws PersistenceException {

			ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
					downBooking, downDeposit);
			try {

				aField.setScoreSystem(scoreSystem);

			} catch (NullPointerException e) {
				throw (new PersistenceException(
						"Error creando nueva cancha. La cancha no fue inicializada con el mensaje Build"));
			}

		}

		public static void addTimeTable(DateTime startMon, DateTime endMon,
				DateTime startTues, DateTime endTues, DateTime startWed,
				DateTime endWed, DateTime startThurs, DateTime endThurs,
				DateTime startFri, DateTime endFri, DateTime startSat,
				DateTime endSat, DateTime startSun, DateTime endSun)
				throws InvalidScheduleException, PersistenceException {

			if (startMon == null || endMon == null || startMon.isAfter(endMon)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para dia Lunes incorrectas"));
			}
			if (startTues == null || endTues == null
					|| startTues.isAfter(endTues)) {
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
			Availability wedAvailability = new Availability(
					DayOfWeek.WEDNESDAY, wedSchedule);
			aCalendar.add(wedAvailability);

			Schedule thursSchedule = new Schedule(startThurs, endThurs);
			Availability thursAvailability = new Availability(
					DayOfWeek.THURSDAY, thursSchedule);
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

				aField.setTimeTable(aCalendar);

			} catch (NullPointerException e) {
				throw (new PersistenceException(
						"Error creando nueva cancha. La cancha no fue inicializada con el mensaje Build"));
			}
		}
		
		public static void saveField() throws PersistenceException, IllegalStateException {

			if(aField == null){
				throw new IllegalStateException("La cancha no fue construida de la manera correcta");
			}
			FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

			fieldDAO.save(aField);

		}

	}

}
