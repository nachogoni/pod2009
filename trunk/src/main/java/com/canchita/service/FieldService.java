package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.FieldMemoryMock;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;

public class FieldService implements FieldServiceProtocol {

	public void deleteField(Long id) {
		(new FieldMemoryMock()).delete(id);
	}

	public Collection<Field> listField() {
		Collection<Field> fields = (new FieldMemoryMock()).getAll();
		System.out.println(fields.toString());
		return fields;
	}
	
	public Collection<Field> listField(Long idComplex) throws ValidationException {
		
		FieldDAO fieldDAO = new FieldMemoryMock();

		return fieldDAO.getFiltered(idComplex);
		
	}

	public Collection<Field> listField(String filter) throws ValidationException {

		Validator validator = new IsAlphaNum(true);

		if (!validator.validate(filter)) {
			throw new ValidationException(
					"Error en el criterio de búsqueda, el mismo debe ser alfanumérico");
		}

		FieldDAO fieldDAO = new FieldMemoryMock();

		return fieldDAO.getFiltered(filter);

	}
	
	public void saveField(Field field) {
		(new FieldMemoryMock()).save(field);
	}

	public void updateField(Field field) {
		(new FieldMemoryMock()).update(field);
	}

	@Override
	public Iterator<Schedule> getAvailableHours(Long id, DateTime date) {

		FieldDAO fieldDAO = new FieldMemoryMock();

		Field field = fieldDAO.getById(id);

		return field.getAvailableHours(date);

	}

}
