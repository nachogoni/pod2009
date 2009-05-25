package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Schedule;
import com.canchita.model.field.Field;

public interface FieldServiceProtocol {

	public Collection<Field> listField();
	
	public Collection<Field> listField(String filter);
	
	public void deleteField(Long id);
	
	public void saveField(Field aField);
	
	public void updateField(Field aField);

	public Iterator<Schedule> getAvailableHours(Long id, DateTime date);
	
}
