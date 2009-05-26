package com.canchita.service;

import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;

public interface ComplexServiceProtocol {

	public Collection<Complex> listComplex();

	public Collection<Complex> listComplex(String filter)
			throws ValidationException;

	public void deleteComplex(Long id) throws PersistenceException;

	public Long saveComplex(String name, String description, String address,
			String zipCode, String neighbourhood, String town, String state,
			String country) throws PersistenceException;

	public void updateComplex(Long id, String name, String description,
			String address, String zipCode, String neighbourhood, String town,
			String state, String country) throws PersistenceException;

	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit) throws PersistenceException;

	public Complex getById(Long id) throws PersistenceException;
 
	
	public void addExpiration(Long id, Integer bookingLimit, Integer depositLimit) throws PersistenceException, InvalidParameterException;
	
	public void addTimeTable(Long id, DateTime startMon, DateTime endMon,
			DateTime startTues, DateTime endTues, DateTime startWed,
			DateTime endWed, DateTime startThurs, DateTime endThurs,
			DateTime startFri, DateTime endFri, DateTime startSat,
			DateTime endSat, DateTime startSun, DateTime endSun) throws InvalidScheduleException, PersistenceException;
}
