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
import com.canchita.model.exception.FieldException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public interface FieldServiceProtocol {

	public Collection<Field> listField() throws PersistenceException;

	public Collection<Field> listField(String filter)
			throws ValidationException, PersistenceException;

	public Collection<Field> getLastFields(String province, String locality,
			String neighbourhood, Long listCount) throws ValidationException,
			PersistenceException;

	public void deleteField(Long id) throws PersistenceException,
			FieldException;

	public Long saveField(String name, String description, Long idComplex,
			Boolean hasRoof, FloorType floor) throws PersistenceException;

	public void updateField(Long id, String name, String description,
			Long number_of_players, Boolean hasRoof, FloorType floor,
			BigDecimal price, BigDecimal bookingPercentage)
			throws PersistenceException;

	public Iterator<Schedule> getAvailableHours(Long id, DateTime date)
			throws PersistenceException;

	public Field getById(Long id) throws PersistenceException;

	List<Booking> getBookings(Long fieldId) throws PersistenceException;

	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException,
			InvalidParameterException;

	public Long getComplexId(Long id) throws PersistenceException;

	Collection<Field> listField(String searchName, String searchDescription,
			String searchMaxPrice, String searchNumberOfPlayers,
			String searchHasRoof, String searchFloorType,
			String searchNeighbourhood, String searchTown, String searchState,
			String searchCountry, String searchAddress, DateTime startTime,
			DateTime endTime) throws ValidationException, PersistenceException;

	public Collection<Expiration> listExpirationPolicies(Long fieldId)
			throws PersistenceException;

	public void deleteExpirationPolicy(Long id) throws PersistenceException;

	public Expiration getExpirationPolicy(Long expirationId)
			throws PersistenceException;

	public void updateExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException;

	public Iterator<Schedule> getAllHours(Long id, DateTime date)
			throws PersistenceException;
}
