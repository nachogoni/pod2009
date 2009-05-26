package com.canchita.service;

import java.util.Collection;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.ComplexMemoryMock;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.complex.Complex;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.location.Place;

public class ComplexService implements ComplexServiceProtocol {

	public void deleteComplex(Long id) {

		(new ComplexMemoryMock()).delete(id);
	}

	public Collection<Complex> listComplex() {
		Collection<Complex> complexes = (new ComplexMemoryMock()).getAll();
		System.out.println(complexes.toString());
		return complexes;
	}

	public Collection<Complex> listComplex(String filter)
			throws ValidationException {

		Validator validator = new IsAlphaNum(true);

		if (!validator.validate(filter)) {

			throw new ValidationException(
					"Error en el criterio de búsqueda, el mismo debe ser alfanumérico");

		}

		ComplexDAO complexDAO = new ComplexMemoryMock();

		return complexDAO.getFiltered(filter);

	}

	public Long saveComplex(String name, String description, String address,
			String zipCode, String neighbourhood, String town, String state,
			String country) {

		Complex aComplex = new Complex(name);
		aComplex.setDescription(description);
		Place.Builder placeBuilder = new Place.Builder(address, neighbourhood);

		Place complexLocation = new Place(placeBuilder);

		complexLocation.setCountry(country);
		complexLocation.setState(state);
		complexLocation.setTown(town);
		complexLocation.setZipCode(zipCode);

		aComplex.setPlace(complexLocation);

		(new ComplexMemoryMock()).save(aComplex);

		return aComplex.getId();

	}

	public void updateComplex(Long id, String name, String description,
			String address, String zipCode, String neighbourhood, String town,
			String state, String country) {

		Complex aComplex = getById(id);
		Place location = aComplex.getPlace();

		if (name != null) {
			aComplex.setName(name);
		}
		if (description != null) {
			aComplex.setDescription(description);
		}
		if (address != null) {
			location.setAddress(address);
		}
		if (zipCode != null) {
			location.setZipCode(zipCode);
		}
		if (neighbourhood != null)
			location.setNeighbourhood(neighbourhood);
		if (town != null) {
			location.setTown(town);
		}
		if (state != null) {
			location.setState(state);
		}
		if (country != null) {
			location.setCountry(country);
		}

		aComplex.setPlace(location);
		(new ComplexMemoryMock()).update(aComplex);

	}

	@Override
	public Complex getById(Long id) {
		return (new ComplexMemoryMock()).getById(id);
	}

	@Override
	public void addScoreSystem(Long id, Integer booking, Integer deposit,
			Integer pay, Integer downBooking, Integer downDeposit) {

		ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
				downBooking, downDeposit);
		Complex aComplex = getById(id);

		aComplex.setScoreSystem(scoreSystem);

		(new ComplexMemoryMock()).update(aComplex);

	}

}
