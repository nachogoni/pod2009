package com.canchita.DAO;

import java.util.Collection;
import java.util.List;

import com.canchita.model.complex.Complex;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Locatable;

public interface ComplexDAO {

	public void save(Complex aComplex) throws PersistenceException;
	
	public Complex getById(Long id) throws PersistenceException;
	
	public void update(Complex aComplex) throws PersistenceException;
	
	public void delete(Long id) throws PersistenceException;
	
	public Collection<Complex> getAll();
	
	public List<Complex> getFiltered(String name, Locatable aLocation );

	public Collection<Complex> getFiltered(String filter);

	public boolean exists(Long idComplex);

}
