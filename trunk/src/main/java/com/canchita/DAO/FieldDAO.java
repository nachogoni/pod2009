package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

public interface FieldDAO {

	public void save(Field aField) throws PersistenceException;

	public Field getById(Long id) throws ElementNotExistsException;

	public void update(Field aField) throws ElementNotExistsException, PersistenceException;

	public void delete(Long id) throws ElementNotExistsException;

	public Collection<Field> getAll();

	public Collection<Field> getFiltered(String filter);

	public Collection<Field> getFiltered(Long idComplex)
			throws PersistenceException;

	public boolean exists(Field field);

}
