package com.canchita.service;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Iterator;

import org.joda.time.DateTime;

import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public interface FieldServiceProtocol {

	public Collection<Field> listField() throws PersistenceException;

	public Collection<Field> listField(String filter)
			throws ValidationException, PersistenceException;

	public void deleteField(Long id) throws PersistenceException;

	public Long saveField(String name, String description, Long idComplex,
			Boolean hasRoof, FloorType floor) throws PersistenceException;

	public void updateField(Long id, String name, String description,
			Boolean hasRoof, FloorType floor) throws PersistenceException;

	public Iterator<Schedule> getAvailableHours(Long id, DateTime date)
			throws PersistenceException;

	public Field getById(Long id) throws PersistenceException;

	Iterator<Booking> getBookings(Long fieldId) throws PersistenceException;

	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException,
			InvalidParameterException;

	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException;

	public Long getComplexId(Long id) throws PersistenceException;

	void addTimeTable(Long id, DateTime startMon, DateTime endMon,
			DateTime startTues, DateTime endTues, DateTime startWed,
			DateTime endWed, DateTime startThurs, DateTime endThurs,
			DateTime startFri, DateTime endFri, DateTime startSat,
			DateTime endSat, DateTime startSun, DateTime endSun)
			throws InvalidScheduleException, PersistenceException;

	public Collection<Field> listField(String searchName,
			String searchDescription, String searchMaxPrice,
			String searchNumberOfPlayers, String searchHasRoof,
			String searchFloorType) throws ValidationException,
			PersistenceException;
}
