package com.canchita.service;

import java.util.Collection;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.ComplexMemoryMock;
import com.canchita.helper.validator.IsAlphaNum;
import com.canchita.helper.validator.Validator;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ValidationException;

public class ComplexService implements ComplexServiceProtocol {

	public void deleteComplex(Long id) {
		(new ComplexMemoryMock()).delete(null);
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

	public void saveComplex(Complex complex) {
		// TODO Auto-generated method stub

	}

	public void updateComplex(Complex complex) {
		// TODO Auto-generated method stub

	}

}
