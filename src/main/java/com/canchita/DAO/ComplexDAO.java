package com.canchita.DAO;

import java.util.Collection;
import java.util.List;
import com.canchita.model.complex.Complex;
import com.canchita.model.location.Locatable;

public interface ComplexDAO {

	public void save(Complex aComplex);
	
	public Complex getById(Long id);
	
	public void update(Complex aComplex);
	
	public void delete(Long id);
	
	public Collection<Complex> getAll();
	
	public List<Complex> getFiltered(String name, Locatable aLocation );
	
}
