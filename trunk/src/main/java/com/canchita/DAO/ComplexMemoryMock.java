package com.canchita.DAO;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.canchita.model.complex.Complex;
import com.canchita.model.location.Locatable;

public class ComplexMemoryMock implements ComplexDAO {

	
	private static Map<Long,Complex> complexMocks;
		
	static {
		ComplexMemoryMock.complexMocks = new HashMap<Long, Complex>();		
	}
	
	public ComplexMemoryMock(){
		
	}
	
	@Override
	public void delete(Complex complex) {
		ComplexMemoryMock.complexMocks.remove(complex.getId());
	}

	@Override
	public Collection<Complex> getAll() {
		return ComplexMemoryMock.complexMocks.values();
	}

	@Override
	public Complex getById(Long id) {
		return ComplexMemoryMock.complexMocks.get(id);
	}

	@Override
	public List<Complex> getFiltered(String name, Locatable location) {
		return null;
	}

	@Override
	public void save(Complex complex) {
		ComplexMemoryMock.complexMocks.put(complex.getId(), complex);
	}

	@Override
	public void update(Complex complex) {
		save(complex);
	}

}
