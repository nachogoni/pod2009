package com.canchita.service;

import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.ExpirationDAO;
import com.canchita.DAO.TimetableDAO;
import com.canchita.DAO.db.TimetableDB;
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
import com.canchita.model.exception.ComplexException;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.location.Place;

public class ComplexService implements ComplexServiceProtocol {

	Complex aComplex;

	public void deleteComplex(Long id) throws PersistenceException, ComplexException {

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		BookingDAO bookingDAO = DAOFactory.get(DAO.BOOKING);
		
		if( bookingDAO.complexHasBookings(id) ) {
			throw new ComplexException("No se puede borrar un complejo que tiene reservas activas");
		}
		
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

		if (filter != null && !validator.validate(filter)) {

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
		if (neighbourhood != null) {
			location.setNeighbourhood(neighbourhood);
		}
		if (town != null) {
			location.setTown(town);
		}
		if (state != null) {
			location.setState(state);
		}
		if (country != null) {
			location.setCountry(country);
		}

		try {
			
		aComplex.setPlace(location);

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		complexDAO.update(aComplex);

		} catch (PersistenceException e) {
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
		}
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

		public static void addExpiration(Integer bookingLimit,
				Integer depositLimit) throws PersistenceException {
			Expiration anExpiration = new Expiration();

			if (bookingLimit == null || bookingLimit < 0
					|| depositLimit == null || depositLimit < 0)
				throw new IllegalArgumentException(
						"Valores de caducidad invalidos");

			if (bookingLimit < depositLimit) {
				throw new IllegalArgumentException(
						"El valor de caducidad de seña no puede ser mayor al valor de caducidad de la reserva");
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

		public static void setExpirationPolicy(Integer scoreFrom,
				Integer scoreTo, Integer downBooking, Integer downDeposit)
				throws PersistenceException {

			if (downBooking == null || downBooking < 0 || downDeposit == null
					|| downDeposit < 0)
				throw new IllegalArgumentException(
						"Valores de caducidad inválidos");

			if (downBooking < downDeposit) {
				throw new IllegalArgumentException(
						"El valor de caducidad de seña no puede ser mayor al "
								+ "valor de caducidad de la reserva");
			}

			Expiration expiration = new Expiration(0, scoreFrom, scoreTo,
					downDeposit, downBooking);
			ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
			expirationDAO.save(aComplex, expiration);

		}

		public static void addTimeTable(DateTime startMon, DateTime endMon,
				DateTime startTues, DateTime endTues, DateTime startWed,
				DateTime endWed, DateTime startThurs, DateTime endThurs,
				DateTime startFri, DateTime endFri, DateTime startSat,
				DateTime endSat, DateTime startSun, DateTime endSun)
				throws InvalidScheduleException, PersistenceException {

			if (startMon == null || endMon == null || startMon.isAfter(endMon)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Lunes incorrectas"));
			}
			if (startTues == null || endTues == null
					|| startTues.isAfter(endTues)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Martes incorrectas"));
			}
			if (startThurs == null || endThurs == null
					|| startThurs.isAfter(endThurs)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Jueves incorrectas"));
			}
			if (startWed == null || endWed == null || startWed.isAfter(endWed)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Miércoles incorrectas"));
			}
			if (startFri == null || endFri == null || startFri.isAfter(endFri)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Viernes incorrectas"));
			}
			if (startSat == null || endSat == null || startSat.isAfter(endSat)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Sábado incorrectas"));
			}
			if (startSun == null || endSun == null || startSun.isAfter(endSun)) {
				throw (new InvalidScheduleException(
						"Fechas de inicio para día Domingo incorrectas"));
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

		static public Long saveComplex() throws PersistenceException,
				IllegalStateException {
			if (aComplex == null) {
				throw new IllegalStateException(
						"El complejo no fue construido correctamente.");
			}

			try {

				ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

				// Guardo los tels
				List<String> phones = aComplex.getPhones();
				// Guardo el calendario
				Calendar aCalendar = aComplex.getTimeTable();

				// Salvo el complejo
				complexDAO.save(aComplex);

				// Recupero Unique
				aComplex = complexDAO.getByPlace(aComplex.getPlace());

				// Agrego los teléfonos.
				for (String phone : phones) {
					complexDAO.addPhone(aComplex, phone);
				}

				// Agrego el calendario.
				// HACK: debería hacerse con el get(DAO.TIMETABLE)
				// pero no lo pude hacer andar :(
				for (Availability av : aCalendar.getAvailabilities()) {
					TimetableDB.getInstance().save(av, aComplex.getId());
				}

			} catch (PersistenceException e) {
				throw e;
			}

			return aComplex.getId();

		}

		public static void addTelephones(String[] telephones) {
			for (String telephone : telephones) {
				aComplex.setPhone(telephone);
			}

		}
	}

	@Override
	public void addTelephones(Long id, String[] telephones)
			throws PersistenceException {

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		try {
			Complex aComplex = complexDAO.getById(id);

			complexDAO.deletePhones(aComplex);

			for (String telephone : telephones) {
				if (telephone != null && !telephone.equals(""))
					complexDAO.addPhone(aComplex, telephone);
			}

		} catch (Exception e) {
			throw new PersistenceException("Complejo no encontrado");
		}
	}

	@Override
	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException {

		// ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
		// downBooking, downDeposit);
		//
		// Complex aComplex = getById(id);
		// aComplex.setScoreSystem(scoreSystem);
		//
		// ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);
		//
		// complexDAO.update(aComplex);

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
					"Fechas de inicio para día Lunes incorrectas"));
		}
		if (startTues == null || endTues == null || startTues.isAfter(endTues)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para día Martes incorrectas"));
		}
		if (startThurs == null || endThurs == null
				|| startThurs.isAfter(endThurs)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para día Jueves incorrectas"));
		}
		if (startWed == null || endWed == null || startWed.isAfter(endWed)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para día Miércoles incorrectas"));
		}
		if (startFri == null || endFri == null || startFri.isAfter(endFri)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para día Viernes incorrectas"));
		}
		if (startSat == null || endSat == null || startSat.isAfter(endSat)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para día Sábado incorrectas"));
		}
		if (startSun == null || endSun == null || startSun.isAfter(endSun)) {
			throw (new InvalidScheduleException(
					"Fechas de inicio para día Domingo incorrectas"));
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

		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

		Complex aComplex = complexDAO.getById(id);
		
		TimetableDAO timetable = DAOFactory.get(DAO.TIME_TABLE);
		
		timetable.deleteByComplex(aComplex.getId());
		
		for (Availability av : aCalendar.getAvailabilities()) {
			timetable.save(av, aComplex.getId());
		}

	}

	@Override
	@Deprecated
	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException {
		Expiration anExpiration = new Expiration();

		if (bookingLimit == null || bookingLimit < 0 || depositLimit == null
				|| depositLimit < 0)
			throw new IllegalArgumentException("Valores de caducidad inválidos");

		if (bookingLimit < depositLimit) {
			throw new IllegalArgumentException(
					"El valor de caducidad de seña no puede ser mayor al valor de caducidad de la reserva");
		}

		anExpiration.setBookingLimit(bookingLimit);
		anExpiration.setDepositLimit(depositLimit);

		try {
			ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

			Complex aComplex = complexDAO.getById(id);

		} catch (Exception e) {
			throw new PersistenceException("Complejo no encontrado");
		}

	}

	@Override
	public Expiration getExpirationPolicy(Long complexId, long score)
			throws PersistenceException {
		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);

		Complex complex = complexDAO.getById(complexId);
		return expirationDAO.getByScore(complex, score);
	}

	@Override
	public Collection<Expiration> listExpirationPolicies(Long complexId)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		Complex complex = new Complex(complexId);
		return expirationDAO.getAll(complex);

	}

	public Expiration getExpirationPolicy(Long expirationId)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		return expirationDAO.getById(expirationId);
	}

	public void setExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException {
		Complex complex = new Complex(id);
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		Expiration expiration = new Expiration(id, scoreFrom, scoreTo,
				downDeposit, downBooking);
		expirationDAO.save(complex, expiration);

	}

	public void updateExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		Expiration expiration = new Expiration(id, scoreFrom, scoreTo,
				downDeposit, downBooking);
		expirationDAO.update(expiration);

	}

	public void deleteExpirationPolicy(Long id) throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		expirationDAO.delete(id);
	}

	public Expiration getDefaultExpirationPolicy(Long id)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);

		return expirationDAO.getDefaultForComplex(id);
	}

	public void setDefaultExpiration(Long complexId, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		expirationDAO.updateDefault(complexId, bookingLimit, depositLimit);

	}

	@Override
	public Collection<String> getNeighbourhoods(String province, String locality) throws PersistenceException {
		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);
		Collection<String> neighbourhoods = complexDAO.getNeighbourhoods(province, locality);
		return neighbourhoods;

	}
	
	@Override
	public Collection<String> getLocations(String province)
			throws PersistenceException {
		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);
		Collection<String> locations = complexDAO.getLocations(province);
		return locations;

	}

	@Override
	public Collection<String> getProvinces() throws PersistenceException {
		ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);
		Collection<String> provinces = complexDAO.getProvinces();
		return provinces;

	}
}
