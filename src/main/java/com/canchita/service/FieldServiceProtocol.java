package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;

public interface FieldServiceProtocol {

	public Collection<Field> listField();
	
	public Collection<Field> listField(String filter) throws ValidationException;
	
	public void deleteField(Long id);
	
	public void saveField(Field aField);
	
	public void updateField(Field aField);

	public Iterator<Schedule> getAvailableHours(Long id, DateTime date);
	
}
