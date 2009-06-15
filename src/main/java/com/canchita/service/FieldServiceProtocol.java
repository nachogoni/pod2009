package com.canchita.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public interface FieldServiceProtocol {

	public Collection<Field> listField() throws PersistenceException;

	public Collection<Field> listField(String filter)
			throws ValidationException, PersistenceException;

	public Collection<Field> getLastFields(String neighbourhood, Long listCount)
			throws ValidationException, PersistenceException;
	
	public void deleteField(Long id) throws PersistenceException;

	public Long saveField(String name, String description, Long idComplex,
			Boolean hasRoof, FloorType floor) throws PersistenceException;

	public void updateField(Long id, String name, String description,
			Long number_of_players, Boolean hasRoof, FloorType floor,
			BigDecimal price) throws PersistenceException;

	public Iterator<Schedule> getAvailableHours(Long id, DateTime date)
			throws PersistenceException;

	public Field getById(Long id) throws PersistenceException;

	List<Booking> getBookings(Long fieldId) throws PersistenceException;

	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException,
			InvalidParameterException;

	public Long getComplexId(Long id) throws PersistenceException;

	public Collection<Field> listField(String searchName,
			String searchDescription, String searchMaxPrice,
			String searchNumberOfPlayers, String searchHasRoof,
			String searchFloorType) throws ValidationException,
			PersistenceException;

	public Collection<Expiration> listExpirationPolicies(Long fieldId)
			throws PersistenceException;

	public void deleteExpirationPolicy(Long id) throws PersistenceException;

	public Expiration getExpirationPolicy(Long expirationId)
			throws PersistenceException;

	public void updateExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException;
}
