package com.canchita.service;

import java.util.Collection;

import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ValidationException;

public interface ComplexServiceProtocol {

	public Collection<Complex> listComplex();
	
	public Collection<Complex> listComplex(String filter) throws ValidationException;
	
	public void deleteComplex(Long id);
	
	public void saveComplex(Complex aComplex);
	
	public void updateComplex(Complex aComplex);
	
}
