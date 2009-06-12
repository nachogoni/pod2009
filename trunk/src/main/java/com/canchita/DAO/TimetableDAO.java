package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.complex.Availability;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public interface TimetableDAO {

	public void save(Availability av, Long complexId)
	throws PersistenceException;


	public Availability getById(Long id) throws ElementNotExistsException;

	public Collection<Availability> getByComplexId(Long complexId)
			throws ElementNotExistsException;

	public void delete(Long id);

}
