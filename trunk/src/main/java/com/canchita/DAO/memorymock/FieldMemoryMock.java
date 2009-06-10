package com.canchita.DAO.memorymock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;

public class FieldMemoryMock implements FieldDAO {

	private static Map<Long, Field> FieldMocks = new HashMap<Long, Field>();
	private static Long autoincrementalPk = 0L;

	static {

		autoincrementalPk = 0L;
		ComplexDAO complexDAO = null;
		try {
			complexDAO = DAOFactory.get(DAO.COMPLEX);
		} catch (PersistenceException e) {
			e.printStackTrace();			
		}

		Field aField;

		Complex aComplex = null;
		try {
			aComplex = complexDAO.getById(1L);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// now the fields

		aField = new Field("Cancha1", "La de adelante", aComplex, true,
				FloorType.ARTIFICIAL_GRASS, aComplex.getExpiration());
		aField.setId(FieldMemoryMock.autoincrementalPk++);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha2", "La del fondo", aComplex, false,
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(FieldMemoryMock.autoincrementalPk++);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha3", "Adelante, izquierda", aComplex, true,
				FloorType.ARTIFICIAL_GRASS, aComplex.getExpiration());
		aField.setId(FieldMemoryMock.autoincrementalPk++);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha4", "Adelante, derecha", aComplex, true,
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(FieldMemoryMock.autoincrementalPk++);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha5", "Atras, izquierda", aComplex, false,
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(FieldMemoryMock.autoincrementalPk++);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha6", "Atras, derecha", aComplex, true,
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(FieldMemoryMock.autoincrementalPk++);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

	}

	public FieldMemoryMock() {

	}

	@FactoryMethod
	public static FieldDAO getInstance() {
		return new FieldMemoryMock();
	}
	
	public void delete(Long id) throws ElementNotExistsException {

		if (!FieldMocks.containsKey(id)) {
			throw new ElementNotExistsException("La cancha no existe");
		}

		FieldMemoryMock.FieldMocks.remove(id);
	}

	public Collection<Field> getAll() {
		return FieldMemoryMock.FieldMocks.values();
	}

	public Field getById(Long id) throws ElementNotExistsException {

		if (!FieldMocks.containsKey(id)) {
			throw new ElementNotExistsException("La cancha no existe");
		}

		return FieldMemoryMock.FieldMocks.get(id);
	}

	public Collection<Field> getFiltered(String filter) {

		Collection<Field> collection = new ArrayList<Field>();

		filter = filter.toLowerCase();

		for (Field field : FieldMocks.values()) {

			String name = field.getName().toLowerCase();

			if (name.indexOf(filter) != -1) {
				collection.add(field);
			}

		}

		return collection;
	}

	public void save(Field field) throws PersistenceException {
		
		
		if ( this.exists(field) ) {
			throw new PersistenceException("Ya existe la cancha en el sistema");
		}

		field.setId(autoincrementalPk++);
		
		FieldMemoryMock.FieldMocks.put(field.getId(), field);
	}

	public void update(Field field) throws PersistenceException {

		if (!FieldMemoryMock.FieldMocks.containsKey(field.getId())) {
			throw new ElementNotExistsException("La cancha no existe");
		}
		
		for (Field otherField : FieldMocks.values()) {

			
			if (field.getId() != otherField.getId()
					&& field.equals(otherField)) {
				throw new ElementExistsException(
						"Ya existe un complejo con esas características");
			}

		}
		
		FieldMemoryMock.FieldMocks.put(field.getId(), field);
	}

	@Override
	public boolean exists(Field field) {
		return FieldMemoryMock.FieldMocks.containsValue(field);
	}

	@Override
	public Collection<Field> getByComplex(Long idComplex)
			throws PersistenceException {

		ComplexDAO complexDao = DAOFactory.get(DAO.COMPLEX);

		if (!complexDao.exists(idComplex)) {
			throw new ElementNotExistsException("El complejo no existe");
		}

		Collection<Field> collection = new ArrayList<Field>();

		for (Field field : FieldMocks.values()) {

			if (field.getComplex().getId().equals(idComplex)) {
				collection.add(field);
			}

		}

		return collection;
	}

}
