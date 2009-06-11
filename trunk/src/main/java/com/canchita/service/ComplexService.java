package com.canchita.service;

import java.util.Collection;

import org.joda.time.DateTime;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.location.Place;

public class ComplexService implements ComplexServiceProtocol {

	Complex aComplex;

	public void deleteComplex(Long id) throws PersistenceException {

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		complexDAO.delete(id);
	}

	public Collection<Complex> listComplex() throws PersistenceException {

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		Collection<Complex> complexes = complexDAO.getAll();
		return complexes;
	}

	public Collection<Complex> listComplex(String filter)
			throws ValidationException, PersistenceException {

		Validator validator = new IsAlphaNum(true);

		if (!validator.validate(filter)) {

			throw new ValidationException(
					"Error en el criterio de búsqueda, el mismo debe ser alfanumérico");

		}

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		return complexDAO.getFiltered(filter);

	}

	@Deprecated
	public Long saveComplex(String name, String description, String address,
			String zipCode, String neighbourhood, String town, String state,
			String country) throws PersistenceException {

		Complex aComplex = new Complex(name);
		aComplex.setDescription(description);
		Place.Builder placeBuilder = new Place.Builder(address, neighbourhood);

		Place complexLocation = new Place(placeBuilder);

		complexLocation.setCountry(country);
		complexLocation.setState(state);
		complexLocation.setTown(town);
		complexLocation.setZipCode(zipCode);

		aComplex.setPlace(complexLocation);

		try {

			ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

			complexDAO.save(aComplex);
		} catch (PersistenceException e) {
			throw e;
		}

		return aComplex.getId();

	}

	public void updateComplex(Long id, String name, String description,
			String address, String zipCode, String neighbourhood, String town,
			String state, String country) throws PersistenceException {

		Complex aComplex = getById(id);
		Place location = aComplex.getPlace();

		if (name != null) {
			aComplex.setName(name);
		}
		if (description != null) {
			aComplex.setDescription(description);
		}
		if (address != null) {
			location.setAddress(address);
		}
		if (zipCode != null) {
			location.setZipCode(zipCode);
		}
		if (neighbourhood != null)
			location.setNeighbourhood(neighbourhood);
		if (town != null) {
			location.setTown(town);
		}
		if (state != null) {
			location.setState(state);
		}
		if (country != null) {
			location.setCountry(country);
		}

		aComplex.setPlace(location);

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		complexDAO.update(aComplex);

	}

	@Override
	public Complex getById(Long id) throws PersistenceException {

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		return complexDAO.getById(id);
	}

	public static class ComplexBuilder {

		static Complex aComplex = null;

		private ComplexBuilder() {
		}

		// Build a complex with minimal information
		public static void Build(String name, String description,
				String address, String zipCode, String neighbourhood,
				String town, String state, String country) {

			aComplex = new Complex(name);
			aComplex.setDescription(description);
			Place.Builder placeBuilder = new Place.Builder(address,
					neighbourhood);

			Place complexLocation = new Place(placeBuilder);

			complexLocation.setCountry(country);
			complexLocation.setState(state);
			complexLocation.setTown(town);
			complexLocation.setZipCode(zipCode);

			aComplex.setPlace(complexLocation);

		}

		public static void addScoreSystem(Integer booking, Integer deposit,
				Integer pay, Integer downBooking, Integer downDeposit)
				throws PersistenceException {

//			ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
//					downBooking, downDeposit);
//
//			try {
//				aComplex.setScoreSystem(scoreSystem);
//			} catch (NullPointerException e) {
//				throw (new PersistenceException(
//						"Error creando nuevo complejo. El complejo no fue inicializado con el mensaje Build"));
//			}

		}

		public static void addExpiration(Integer bookingLimit,
				Integer depositLimit) throws PersistenceException {
			Expiration anExpiration = new Expiration();

			if (bookingLimit == null || bookingLimit < 0
					|| depositLimit == null || depositLimit < 0)
				throw new IllegalArgumentException(
						"Valores de caducidad invalidos");

			if (depositLimit < bookingLimit) {
				throw new IllegalArgumentException(
						"El valor de caducidad de seña no puede ser menor al valor de caducidad de la reserva");
			}

			anExpiration.setBookingLimit(bookingLimit);
			anExpiration.setDepositLimit(depositLimit);

			try {
				aComplex.setExpiration(anExpiration);
			} catch (NullPointerException e) {
				throw (new PersistenceException(
						"Error creando nuevo complejo. El complejo no fue inicializado con el mensaje Build"));
			}

		}

		public void addTimeTable(DateTime startMon, DateTime endMon,
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
				aComplex.setTimeTable(aCalendar);
			} catch (NullPointerException e) {
				throw (new PersistenceException(
						"Error creando nuevo complejo. El complejo no fue inicializado con el mensaje Build"));
			}

		}

		static public Long saveComplex() throws PersistenceException {
		
			try {

				ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

				complexDAO.save(aComplex);
			} catch (PersistenceException e) {
				throw e;
			}

			return aComplex.getId();
			
		}
	}

	@Override
	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException {

//		ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
//				downBooking, downDeposit);
//
//		Complex aComplex = getById(id);
//		aComplex.setScoreSystem(scoreSystem);
//
//		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);
//
//		complexDAO.update(aComplex);

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
			ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

			Complex aComplex = complexDAO.getById(id);
			aComplex.setTimeTable(aCalendar);
			complexDAO.update(aComplex);
		} catch (Exception e) {
			throw new PersistenceException("Complejo no encontrado");
		}
	}

	@Override
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
			ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

			Complex aComplex = complexDAO.getById(id);
			aComplex.setExpiration(anExpiration);
			complexDAO.update(aComplex);
		} catch (Exception e) {
			throw new PersistenceException("Complejo no encontrado");
		}

	}

}
