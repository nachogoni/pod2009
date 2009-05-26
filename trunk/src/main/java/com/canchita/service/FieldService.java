package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.DAO.ComplexMemoryMock;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.FieldMemoryMock;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ElementNotExistsException;
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
			Boolean hasRoof, FloorType floor, Expiration expiration) throws PersistenceException
			  {

		Field aField = new Field((new ComplexMemoryMock()).getById(idComplex),
				name);

		aField.setDescription(description);
		aField.setHasRoof(hasRoof);
		aField.setFloor(floor);
		aField.setExpiration(expiration);

		(new FieldMemoryMock()).save(aField);

		return aField.getId();

	}

	public void updateField(Long id, String name, String description,
			Long idComplex, Boolean hasRoof, FloorType floor,
			Expiration expiration) throws ElementNotExistsException {

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
		if (expiration != null) {
			aField.setExpiration(expiration);
		}

		(new FieldMemoryMock()).update(aField);
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

}
