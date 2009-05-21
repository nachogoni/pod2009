package com.canchita.service;

import java.util.Collection;

import com.canchita.DAO.ComplexMemoryMock;
import com.canchita.model.complex.Complex;

public class ComplexService implements ComplexServiceProtocol {

	
	public void deleteComplex(Long id) {
		(new ComplexMemoryMock()).delete(null);
	}

	public Collection<Complex> listComplex() {		
		Collection<Complex> complexes= (new ComplexMemoryMock()).getAll(); 
		System.out.println(complexes.toString());
		return complexes;
	}

	
	public Collection<Complex> listComplex(String filter) {
		return null;
	}

	
	public void saveComplex(Complex complex) {
		// TODO Auto-generated method stub

	}

	
	public void updateComplex(Complex complex) {
		// TODO Auto-generated method stub

	}

}
