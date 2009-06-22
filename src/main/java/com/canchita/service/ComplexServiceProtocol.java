package com.canchita.service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.util.Collection;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ComplexException;
import com.canchita.model.exception.InvalidScheduleException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;

public interface ComplexServiceProtocol {

	public Collection<Complex> listComplex() throws PersistenceException;

	public Collection<Complex> listComplex(String filter)
			throws ValidationException, PersistenceException;

	public Collection<String> getProvinces() throws PersistenceException;

	public Collection<String> getLocations(String province)
			throws PersistenceException;

	public Collection<String> getNeighbourhoods(String province, String locality)
			throws PersistenceException;

	public void deleteComplex(Long id) throws PersistenceException,
			ComplexException;

	public Long saveComplex(String name, String description, String address,
			String zipCode, String neighbourhood, String town, String state,
			String country) throws PersistenceException;

	public void updateComplex(Long id, String name, String description,
			String address, String zipCode, String neighbourhood, String town,
			String state, String country, BigDecimal bookingPercentage)
			throws PersistenceException;

	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit)
			throws PersistenceException;

	public Complex getById(Long id) throws PersistenceException;

	public Expiration getExpirationPolicy(Long complexId, long score)
			throws PersistenceException;

	public Expiration getExpirationPolicy(Long expirationId)
			throws PersistenceException;

	public void addExpiration(Long id, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException,
			InvalidParameterException;

	public void addTimeTable(Long id, DateTime startMon, DateTime endMon,
			DateTime startTues, DateTime endTues, DateTime startWed,
			DateTime endWed, DateTime startThurs, DateTime endThurs,
			DateTime startFri, DateTime endFri, DateTime startSat,
			DateTime endSat, DateTime startSun, DateTime endSun)
			throws InvalidScheduleException, PersistenceException;

	Collection<Expiration> listExpirationPolicies(Long complexId)
			throws PersistenceException;

	public void updateExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException;

	public void setExpirationPolicy(Long id, Integer scoreFrom,
			Integer scoreTo, Integer downBooking, Integer downDeposit)
			throws PersistenceException;

	public void deleteExpirationPolicy(Long id) throws PersistenceException;

	public Expiration getDefaultExpirationPolicy(Long id)
			throws PersistenceException;

	public void addTelephones(Long id, String[] telephones)
			throws PersistenceException;

}
