package com.canchita.DAO;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Complex;
import com.canchita.model.location.Locatable;

public interface ComplexDAO {

	public void save(Complex aComplex);
	
	public Complex getById(Long id);
	
	public void update(Complex aComplex);
	
	public void delete(Long id);
	
	public Collection<Complex> getAll();
	
	public List<Complex> getFiltered(String name, Locatable aLocation );

	public Collection<Complex> getFiltered(String filter);

}
