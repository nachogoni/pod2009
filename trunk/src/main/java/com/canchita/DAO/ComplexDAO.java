package com.canchita.DAO;

import java.util.Collection;
import java.util.List;

import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Place;

public interface ComplexDAO {

	public void save(Complex aComplex) throws PersistenceException;

	public Complex getById(Long id) throws PersistenceException;

	public Complex getByPlace(Place aPlace) throws ElementNotExistsException;

	public void update(Complex aComplex) throws PersistenceException;

	public void delete(Long id) throws PersistenceException;

	public Collection<Complex> getAll();

	public Collection<Complex> getFiltered(String filter);

	public boolean exists(Long idComplex);

	void addPhone(Complex complex, String phone)
			throws ElementNotExistsException, PersistenceException;

	void updatePhone(Complex complex, String oldPhone, String newPhone)
			throws ElementNotExistsException, PersistenceException;

	List<String> getPhones(Complex complex);

}
