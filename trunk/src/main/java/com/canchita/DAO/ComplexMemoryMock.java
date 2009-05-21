package com.canchita.DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.location.Locatable;
import com.canchita.model.location.Place;

public class ComplexMemoryMock implements ComplexDAO {

	private static Map<Long, Complex> complexMocks = new HashMap<Long, Complex>();

	static {
		// Initialize an element for mocking purposes
		Complex aComplex = new Complex("Lo de Tincho");

		Calendar titos_horarios = new Calendar();
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

		ComplexMemoryMock.complexMocks.put(1L, aComplex);
	}

	public ComplexMemoryMock() {

	}
	
	
	public void delete(Long id) {
		ComplexMemoryMock.complexMocks.remove(id);
	}

	public Collection<Complex> getAll() {
		return ComplexMemoryMock.complexMocks.values();
	}

	public Complex getById(Long id) {
		return ComplexMemoryMock.complexMocks.get(id);
	}

	public List<Complex> getFiltered(String name, Locatable location) {
		return null;
	}

	public void save(Complex complex) {
		ComplexMemoryMock.complexMocks.put(complex.getId(), complex);
	}

	public void update(Complex complex) {
		save(complex);
	}

}
