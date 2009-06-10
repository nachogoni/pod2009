package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.complex.Complex;
import com.canchita.model.exception.PersistenceException;

public interface ComplexDAO {

	public void save(Complex aComplex) throws PersistenceException;
	
	public Complex getById(Long id) throws PersistenceException;
	
	public void update(Complex aComplex) throws PersistenceException;
	
	public void delete(Long id) throws PersistenceException;
	
	public Collection<Complex> getAll();
	
	public Collection<Complex> getFiltered(String filter);

	public boolean exists(Long idComplex);

}
