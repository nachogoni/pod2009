package com.canchita.DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canchita.model.complex.Complex;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;
import com.canchita.model.location.Locatable;

public class FieldMemoryMock implements FieldDAO {

	private static Map<Long, Field> FieldMocks = new HashMap<Long, Field>();

	static {
		// Initialize an element for mocking purposes
		Complex complex;
		Field aField;

		complex = new Complex("The Tito's Complex");
		
		aField = new Field("Cancha_1", "La de adelante", complex, true,
				FloorType.ARTIFICIAL_GRASS, complex.getExpiration());
		aField.setId(1L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
		aField = new Field("Cancha_2", "La del fondo", complex, false,
				FloorType.CONCRETE, complex.getExpiration());
		aField.setId(2L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		complex = new Complex("La casa de la nona");
		
		aField = new Field("Cancha_1", "Adelante, izquierda", complex, true,
				FloorType.ARTIFICIAL_GRASS, complex.getExpiration());
		aField.setId(3L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha_2", "Adelante, derecha", complex, true, 
				FloorType.CONCRETE, complex.getExpiration());
		aField.setId(4L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
		aField = new Field("Cancha_3", "Atras, izquierda", complex, false, 
				FloorType.CONCRETE, complex.getExpiration());
		aField.setId(5L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
		aField = new Field("Cancha_4", "Atras, derecha", complex, true, 
				FloorType.CONCRETE, complex.getExpiration());
		aField.setId(6L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
	}
	
	public FieldMemoryMock() {

	}
	
	public void delete(Long id) {
		FieldMemoryMock.FieldMocks.remove(id);
	}

	public Collection<Field> getAll() {
		return FieldMemoryMock.FieldMocks.values();
	}

	public Field getById(Long id) {
		return FieldMemoryMock.FieldMocks.get(id);
	}

	public List<Field> getFiltered(String name, Locatable location) {
		return null;
	}

	public void save(Field Field) {
		FieldMemoryMock.FieldMocks.put(Field.getId(), Field);
	}

	public void update(Field Field) {
		save(Field);
	}

	@Override
	public boolean exists(Field field) {
		return FieldMemoryMock.FieldMocks.containsValue(field);
	}

}
