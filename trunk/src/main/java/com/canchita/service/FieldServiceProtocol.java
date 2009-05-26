package com.canchita.service;

import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public interface FieldServiceProtocol {

	public Collection<Field> listField();
	
	public Collection<Field> listField(String filter) throws ValidationException;

	public void deleteField(Long id);
	
	public Long saveField(String name, String description, Long idComplex, Boolean hasRoof, FloorType floor, Expiration expiration);
	
	public void updateField(Long id, String name, String description, Long idComplex, Boolean hasRoof, FloorType floor, Expiration expiration);
	
	public Iterator<Schedule> getAvailableHours(Long id, DateTime date);

	public Field getById(Long id);
}
