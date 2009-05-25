package com.canchita.DAO;

import java.util.Collection;
import java.util.List;

import com.canchita.model.field.Field;
import com.canchita.model.location.Locatable;

public interface FieldDAO {

	public void save(Field aField);
	
	public Field getById(Long id);
	
	public void update(Field aField);
	
	public void delete(Long id);
	
	public Collection<Field> getAll();
	
	public List<Field> getFiltered(String name, Locatable aLocation );

	public boolean exists(Field field);
	
}