package com.canchita.DAO;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;
import com.canchita.model.location.Place;

public class FieldMemoryMock implements FieldDAO {

	private static Map<Long, Field> FieldMocks = new HashMap<Long, Field>();
	private static Long autoincrementalPk = 0L;

	static {

		autoincrementalPk = 0L;
		ComplexMemoryMock complexDAO = new ComplexMemoryMock();

		Field aField;

		Calendar titos_horarios = new Calendar();

		Schedule schedule = new Schedule(
				new DateTime(2009, 5, 25, 10, 0, 0, 0), new DateTime(2009, 5,
						25, 13, 0, 0, 0));
		Availability availability = new Availability(DayOfWeek.MONDAY, schedule);

		titos_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009, 5, 25, 13, 0, 0, 0),
				new DateTime(2009, 5, 25, 14, 0, 0, 0));
		availability = new Availability(DayOfWeek.MONDAY, schedule);

		titos_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009, 5, 25, 16, 0, 0, 0),
				new DateTime(2009, 5, 25, 21, 0, 0, 0));
		availability = new Availability(DayOfWeek.MONDAY, schedule);
		titos_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009, 5, 25, 3, 0, 0, 0),
				new DateTime(2009, 5, 25, 5, 0, 0, 0));
		availability = new Availability(DayOfWeek.TUESDAY, schedule);
		titos_horarios.add(availability);

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

	public Collection<Field> getFiltered(Long idComplex)
			throws ElementNotExistsException {

		ComplexDAO complexDao = new ComplexMemoryMock();

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
		if (FieldMemoryMock.FieldMocks.containsKey(field.getId()) || this.exists(field)) {
			throw new PersistenceException("Ya existe la cancha en el sistema");
		}

		FieldMemoryMock.FieldMocks.put(field.getId(), field);
	}

	public void update(Field field) throws PersistenceException {

		if (!FieldMemoryMock.FieldMocks.containsKey(field.getId())) {
			throw new ElementNotExistsException("La cancha no existe");
		}
		
		save(field);
	}

	@Override
	public boolean exists(Field field) {
		return FieldMemoryMock.FieldMocks.containsValue(field);
	}

}
