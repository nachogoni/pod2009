package com.canchita.service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.ExpirationDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.IsNumeric;
import com.canchita.helper.validator.Validator;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
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

	public Collection<Field> getLastFields(String neighbourhood, Long listCount)
			throws ValidationException, PersistenceException {
		
		Validator validator = new IsAlphaNum(true);
		
		if (neighbourhood != null && !validator.validate(neighbourhood)) {
			throw new ValidationException(
			"Error en el criterio de búsqueda, el Barrio debe ser alfanumérico");
		}
		
		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);
		
		return fieldDAO.getLastFields(neighbourhood, listCount);
		
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
			Long number_of_players, Boolean hasRoof, FloorType floor,
			BigDecimal price) throws PersistenceException {

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
		if (price != null) {
			aField.setPrice(price);
		}
		if (number_of_players != null) {
			aField.setNumberOfPlayers(number_of_players);
		}

		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

		fieldDAO.update(aField);
	}

	@Deprecated
	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException {
		Expiration anExpiration = new Expiration();

		if (bookingLimit == null || bookingLimit < 0 || depositLimit == null
				|| depositLimit < 0)
			throw new IllegalArgumentException("Valores de caducidad invalidos");

		if (bookingLimit < depositLimit) {
			throw new IllegalArgumentException(
					"El valor de caducidad de seña no puede ser mayor al valor de caducidad de la reserva");
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
	public List<Booking> getBookings(Long fieldId)
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

	public static class FieldBuilder {
		static Field aField = null;

		public static void Build(String name, String description,
				Long idComplex, Boolean hasRoof, FloorType floor, BigDecimal price,
				Long number_of_players) throws PersistenceException {

			ComplexDAO complexDAO = DAOFactory.get(DAO.COMPLEX);

			aField = new Field(complexDAO.getById(idComplex), name);

			aField.setDescription(description);
			aField.setHasRoof(hasRoof);
			aField.setFloor(floor);
			aField.setPrice(price);
			aField.setNumberOfPlayers(number_of_players);

		}

		public static void saveField() throws PersistenceException,
				IllegalStateException {

			if (aField == null) {
				throw new IllegalStateException(
						"La cancha no fue construida de la manera correcta");
			}
			FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);

			fieldDAO.save(aField);

		}

	}

	@Override
	public Collection<Expiration> listExpirationPolicies(Long fieldId)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		Field field = new Field(fieldId);
		return expirationDAO.getAll(field);

	}

	public void deleteExpirationPolicy(Long id) throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		expirationDAO.delete(id);
	}

	public void setExpirationPolicy(Long fieldId, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException {
		FieldDAO fieldDAO = DAOFactory.get(DAO.FIELD);
		Field field = fieldDAO.getById(fieldId);
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		Expiration expiration = new Expiration(0, scoreFrom, scoreTo,
				downDeposit, downBooking);

		expirationDAO.save(field, expiration);

	}

	public Expiration getExpirationPolicy(Long expirationId)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		return expirationDAO.getById(expirationId);
	}

	public void updateExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException {
		ExpirationDAO expirationDAO = DAOFactory.get(DAO.EXPIRATION);
		Expiration expiration = new Expiration(id, scoreFrom, scoreTo,
				downDeposit, downBooking);
		expirationDAO.update(expiration);

	}

}
