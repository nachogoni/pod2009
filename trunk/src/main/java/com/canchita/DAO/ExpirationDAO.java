package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

public interface ExpirationDAO {

	public void save(Field aField, Expiration anExpiration) throws PersistenceException;
	
	public void save(Complex aComplex, Expiration anExpiration) throws PersistenceException;

	public Expiration getById(Long id) throws PersistenceException;

	public Expiration getByScore(Field aField, Integer score) throws ElementNotExistsException;
	
	public Expiration getByScore(Complex aComplex, Integer score) throws ElementNotExistsException;
	
	public void update(Expiration expiration) throws PersistenceException;

	public void delete(Long id) throws PersistenceException;

	public Collection<Expiration> getAll();

	public Collection<Expiration> getAll(Field aField);
	
	public Collection<Expiration> getAll(Complex aComplex);

	public boolean exists(Long id);

	public Expiration getDefaultForComplex(Long id) throws ElementNotExistsException;

	public void updateDefault(Long complexId, Integer bookingLimit,
			Integer depositLimit) throws ElementNotExistsException,
			PersistenceException;

}
