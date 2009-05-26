package com.canchita.DAO;

import java.util.ArrayList;
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
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Locatable;
import com.canchita.model.location.Place;

public class ComplexMemoryMock implements ComplexDAO {

	private static Map<Long, Complex> complexMocks = new HashMap<Long, Complex>();
	private static Long autoincrementalPk = 0L;

	static {
		// Initialize an element for mocking purposes
		autoincrementalPk = 0L;

		Complex aComplex = new Complex("Lo de Tincho");

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

		ScoreSystem titos_scores = new ScoreSystem(10, 20, 30, 10, 20);
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

		aComplex.setId(ComplexMemoryMock.autoincrementalPk++);

		ComplexMemoryMock.complexMocks.put(aComplex.getId(), aComplex);
		
		Complex otherComplex = new Complex("Las canchitas de Roca");

		Calendar roca_horarios = new Calendar();

		schedule = new Schedule(
			new DateTime(2009, 5, 25, 10, 0, 0, 0), new DateTime(2009, 5,
						25, 13, 0, 0, 0));
		availability = new Availability(DayOfWeek.MONDAY, schedule);

		roca_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009, 5, 25, 15, 0, 0, 0),
				new DateTime(2009, 5, 25, 18, 0, 0, 0));
		availability = new Availability(DayOfWeek.MONDAY, schedule);

		roca_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009, 5, 25, 1, 0, 0, 0),
				new DateTime(2009, 5, 25, 3, 0, 0, 0));
		availability = new Availability(DayOfWeek.MONDAY, schedule);
		roca_horarios.add(availability);

		schedule = new Schedule(new DateTime(2009, 5, 25, 3, 0, 0, 0),
				new DateTime(2009, 5, 25, 9, 0, 0, 0));
		availability = new Availability(DayOfWeek.TUESDAY, schedule);
		roca_horarios.add(availability);

		ScoreSystem roca_scores = new ScoreSystem(10, 20, 30, 10, 20);
		Expiration roca_expiran = new Expiration();

		Place anotherPlace = new Place.Builder("Roca 339", "Vicente Lopez").town(
				"Vicente Lopez").state("Buenos Aires").country("Argentina").latitude(
				"-34.030303").longitude("-58.3665").telephone("4791-2234")
				.build();

		otherComplex.setPlace(anotherPlace);
		otherComplex.setDescription("Canchas sobre el río");
		otherComplex.setTimeTable(roca_horarios);
		otherComplex.setScoreSystem(roca_scores);
		// otherComplex.setFields(fields);
		otherComplex.setExpiration(roca_expiran);
		otherComplex.setId(ComplexMemoryMock.autoincrementalPk++);

		ComplexMemoryMock.complexMocks.put(otherComplex.getId(), otherComplex);
	}

	public ComplexMemoryMock() {

	}

	public void delete(Long id) throws PersistenceException {

		System.out.println("Voy a ver si existe el id " + id);
		
		if (! this.exists(id)) {
			System.out.println("No existe");
			throw new ElementNotExistsException("El complejo no existe");
		}

		ComplexMemoryMock.complexMocks.remove(id);
		System.out.println("Lo borre");
	}

	public Collection<Complex> getAll() {
		return ComplexMemoryMock.complexMocks.values();
	}

	public Complex getById(Long id) throws PersistenceException {
		Complex aComplex = ComplexMemoryMock.complexMocks.get(id);

		if (aComplex == null) {
			throw new ElementNotExistsException(
					"Complejo no encontrado en la base de datos");
		}

		return aComplex;
	}

	public List<Complex> getFiltered(String name, Locatable location) {
		return null;
	}

	@Override
	public Collection<Complex> getFiltered(String filter) {

		Collection<Complex> collection = new ArrayList<Complex>();

		filter = filter.toLowerCase();

		for (Complex complex : complexMocks.values()) {

			String name = complex.getName().toLowerCase();

			if (name.indexOf(filter) != -1) {
				collection.add(complex);
			}

		}

		return collection;

	}

	public void save(Complex complex) throws PersistenceException {
		if (this.exists(complex)) {
			throw new ElementExistsException("El complejo ya existe");

		}

		complex.setId(setPrimaryKey());

		ComplexMemoryMock.complexMocks.put(complex.getId(), complex);
	}

	private boolean exists(Complex complex) {
		return ComplexMemoryMock.complexMocks.containsValue(complex);
	}

	private Long setPrimaryKey() {

		ComplexMemoryMock.autoincrementalPk++;
		return ComplexMemoryMock.autoincrementalPk;
	}

	public void update(Complex complex) throws PersistenceException {

		if (!this.exists(complex)) {
			throw new ElementNotExistsException("El complejo no existe");
		}

		System.out.println("Voy a comparar el complejo de id " + complex.getId());
		
		for (Complex otherComplex : complexMocks.values()) {

			System.out.println("Comparo contra " + otherComplex.getId());
			
			if (complex.getId() != otherComplex.getId()
					&& complex.equals(otherComplex)) {
				throw new ElementExistsException(
						"Ya existe un complejo con esas características");
			}

		}

		ComplexMemoryMock.complexMocks.put(complex.getId(), complex);

	}

	@Override
	public boolean exists(Long idComplex) {
		return ComplexMemoryMock.complexMocks.containsKey(idComplex);
	}

}
