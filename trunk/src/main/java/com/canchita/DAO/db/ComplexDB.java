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
		String query = "SELECT * FROM COMPLEX";

		List<Complex> results = executeQuery(query, new Object[] {},
				ComplexBuilder.getInstance());

		return results;
	}

	@Override
	public Complex getById(Long id) throws PersistenceException {
		String query = "SELECT * FROM COMPLEX WHERE \"complex_id\" = ?";

		List<Complex> results = executeQuery(query, new Object[] { id },
				ComplexBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public void save(Complex complex) throws PersistenceException {
		String query = "INSERT into COMPLEX VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		executeUpdate(query, new Object[] { complex.getName(),
				complex.getDescription(), complex.getPlace().getAddress(),
				complex.getPlace().getTown(), complex.getPlace().getState(),
				complex.getPlace().getCountry(), complex.getFax(),
				complex.getEmail(), complex.getPicture(),
				complex.getPlace().getLatitude(),
				complex.getPlace().getLongitude() });
	}

	@Override
	public void update(Complex complex) throws PersistenceException {
		String query = "UPDATE COMPLEX set \"name\" = ?, \"description\" = ?, "
				+ "\"address\" = ?, \"city\" = ?, "
				+ "\"state\" = ?, \"country\" = ?, \"fax\" = ?,"
				+ "\"email\" = ?, \"picture\" = ?, \"latitude\" = ?, \"longitude\" = ?"
				+ "where \"complex_id\" = ?";

		executeUpdate(query, new Object[] { complex.getName(),
				complex.getDescription(), complex.getPlace().getAddress(),
				complex.getPlace().getTown(), complex.getPlace().getState(),
				complex.getPlace().getCountry(), complex.getFax(),
				complex.getEmail(), complex.getPicture(),
				complex.getPlace().getLatitude(),
				complex.getPlace().getLongitude(), complex.getId() });

	}

	@Override
	public Collection<Complex> getFiltered(String filter) {
		String query = "SELECT * FROM COMPLEX WHERE \"name\" LIKE ?";

		List<Complex> results = executeQuery(query, new Object[] { "%" + filter
				+ "%" }, ComplexBuilder.getInstance());

		return results;
	}

}