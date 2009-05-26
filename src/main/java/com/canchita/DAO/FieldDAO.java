package com.canchita.DAO;

import java.util.Collection;

import com.canchita.model.field.Field;

public interface FieldDAO {

	public void save(Field aField);
	
	public Field getById(Long id);
	
	public void update(Field aField);
	
	public void delete(Long id);
	
	public Collection<Field> getAll();
	
	public Collection<Field> getFiltered(String filter);

	public Collection<Field> getFiltered(Long idComplex);
	
	public boolean exists(Field field);
	
}
