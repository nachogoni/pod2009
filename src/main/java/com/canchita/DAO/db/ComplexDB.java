package com.canchita.DAO.db;

import java.util.Collection;
import java.util.List;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.db.builders.ComplexBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public class ComplexDB extends AllDB implements ComplexDAO {
	private static ComplexDB instance;

	static {
		instance = new ComplexDB();
	}

	private ComplexDB() {
	}

	public static ComplexDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Long id) throws PersistenceException {
		String query = "DELETE FROM COMPLEX WHERE \"complex_id\" = ?";
		executeUpdate(query, new Object[] { id });

	}

	@Override
	public boolean exists(Long idComplex) {
		String query = "SELECT COUNT(*) AS COUNT FROM COMPLEX WHERE \"complex_id\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { idComplex },
				CountBuilder.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public Collection<Complex> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Complex getById(Long id) throws PersistenceException {
		// CommonUser list gets loaded.
		String query = "SELECT * FROM COMPLEX WHERE \"complex_id\" = ?";

		List<Complex> results = executeQuery(query,
				new Object[] { id }, ComplexBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public void save(Complex complex) throws PersistenceException {

	}

	@Override
	public void update(Complex complex) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<Complex> getFiltered(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

}