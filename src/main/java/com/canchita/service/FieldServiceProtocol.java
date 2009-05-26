package com.canchita.service;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public interface FieldServiceProtocol {

	public Collection<Field> listField();

	public Collection<Field> listField(String filter)
			throws ValidationException;

	public void deleteField(Long id) throws ElementNotExistsException;

	public Long saveField(String name, String description, Long idComplex,
			Boolean hasRoof, FloorType floor, Expiration expiration)
			throws PersistenceException;

	public void updateField(Long id, String name, String description,
			Long idComplex, Boolean hasRoof, FloorType floor,
			Expiration expiration) throws ElementNotExistsException, PersistenceException;

	public Iterator<Schedule> getAvailableHours(Long id, DateTime date)
			throws ElementNotExistsException;

	public Field getById(Long id) throws ElementNotExistsException;

	Iterator<Booking> getBookings(Long fieldId)
			throws ElementNotExistsException;

	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException,
			InvalidParameterException;

	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException;
}
