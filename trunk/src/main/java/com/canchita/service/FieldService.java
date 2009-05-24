package com.canchita.service;

import java.util.Collection;

import com.canchita.DAO.FieldMemoryMock;
import com.canchita.model.field.Field;

public class FieldService implements FieldServiceProtocol {
	
	public void deleteField(Long id) {
		(new FieldMemoryMock()).delete(id);
	}

	public Collection<Field> listField() {		
		Collection<Field> fields = (new FieldMemoryMock()).getAll(); 
		System.out.println(fields.toString());
		return fields;
	}
	
	public Collection<Field> listField(String filter) {
		return null; //TODO:
	}
	
	public void saveField(Field field) {
		(new FieldMemoryMock()).save(field);
	}

	public void updateField(Field field) {
		(new FieldMemoryMock()).update(field);
	}

}
