package com.canchita.service;

import java.util.Collection;

import com.canchita.model.complex.Complex;

public interface ComplexServiceProtocol {

	public Collection<Complex> listComplex();
	
	public Collection<Complex> listComplex(String filter);
	
	public void deleteComplex(Complex aComplex);
	
	public void saveComplex(Complex aComplex);
	
	public void updateComplex(Complex aComplex);
	
}
