package com.canchita.DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.canchita.model.booking.Expiration;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.DayOfWeek;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.field.Field;
import com.canchita.model.field.FloorType;
import com.canchita.model.location.Locatable;
import com.canchita.model.location.Place;

public class FieldMemoryMock implements FieldDAO {

	private static Map<Long, Field> FieldMocks = new HashMap<Long, Field>();

	static {
		// Initialize an element for mocking purposes - cable guy with ComplexDAO
		Complex aComplex = new Complex("Lo de Tincho");
		Field aField;
		
		Calendar titos_horarios = new Calendar();
		
		Schedule schedule = new Schedule(new DateTime(2009,5,25,10,0,0,0),new DateTime(2009,5,25,13,0,0,0));
		Availability availability = new Availability(DayOfWeek.MONDAY,schedule);
		
		titos_horarios.add(availability);
	
		schedule = new Schedule(new DateTime(2009,5,25,13,0,0,0),new DateTime(2009,5,25,14,0,0,0));
		availability = new Availability(DayOfWeek.MONDAY,schedule);
		
		titos_horarios.add(availability);
	
		schedule = new Schedule(new DateTime(2009,5,25,16,0,0,0),new DateTime(2009,5,25,21,0,0,0));
		availability = new Availability(DayOfWeek.MONDAY,schedule);
		titos_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009,5,25,3,0,0,0),new DateTime(2009,5,25,5,0,0,0));
		availability = new Availability(DayOfWeek.TUESDAY,schedule);
		titos_horarios.add(availability);

		
		ScoreSystem titos_scores = new ScoreSystem();
		Expiration titos_expiran = new Expiration();


		Place place = new Place.Builder("Madero 339", "Puerto Madero").town(
				"CABA").state("Buenos Aires").country("Argentina").latitude(
				"-34.030303").longitude("-58.3665").telephone("4343-4334")
				.telephone("5555-5555").build();

		aComplex.setPlace(place);
		aComplex.setDescription("El complejo mas divertido");
		aComplex.setTimeTable(titos_horarios);
		aComplex.setScoreSystem(titos_scores);
		// aComplex.setFields(fields);
		aComplex.setExpiration(titos_expiran);
		aComplex.setId(1L);
		
		//complex = (new ComplexMemoryMock()).getById(1L);

		//now the fields
		
		aField = new Field("Cancha_1", "La de adelante", aComplex, true,
				FloorType.ARTIFICIAL_GRASS, aComplex.getExpiration());
		aField.setId(1L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
		aField = new Field("Cancha_2", "La del fondo", aComplex, false,
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(2L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		//complex = (new ComplexMemoryMock()).getById(2L);
		aComplex = new Complex("La casa de la nona");
		
		aField = new Field("Cancha_1", "Adelante, izquierda", aComplex, true,
				FloorType.ARTIFICIAL_GRASS, aComplex.getExpiration());
		aField.setId(3L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);

		aField = new Field("Cancha_2", "Adelante, derecha", aComplex, true, 
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(4L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
		aField = new Field("Cancha_3", "Atras, izquierda", aComplex, false, 
				FloorType.CONCRETE, aComplex.getExpiration());
		aField.setId(5L);
		FieldMemoryMock.FieldMocks.put(aField.getId(), aField);
		
		aField = new Field("Cancha_4", "Atras, derecha", aComplex, true, 
				FloorType.CONCRETE, aComplex.getExpiration());
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

	public List<Field> getFiltered(String filter) {
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
