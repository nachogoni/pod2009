package com.canchita.DAO.db;

import java.util.Collection;
import java.util.List;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.db.builders.ComplexBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.PhoneBuilder;
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
		
		for (Complex complex : results) {
			List<String> phones = this.getPhones(complex);

			for (String phone : phones) {
				complex.setPhone(phone);
			}
		}

		return results;
	}

	@Override
	public Complex getById(Long id) throws PersistenceException {
		String query = "SELECT * FROM COMPLEX WHERE \"complex_id\" = ?";

		List<Complex> results = executeQuery(query, new Object[] { id },
				ComplexBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (Complex complex : results) {
			List<String> phones = this.getPhones(complex);

			for (String phone : phones) {
				complex.setPhone(phone);
			}
		}
		
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

		for (Complex complex : results) {
			List<String> phones = this.getPhones(complex);

			for (String phone : phones) {
				complex.setPhone(phone);
			}
		}
		
		return results;
	}

	@Override
	public void addPhone(Complex aComplex, String phone)
			throws ElementNotExistsException, PersistenceException {
		String query = "INSERT INTO PHONE(\"phone\",\"complex_id\") VALUES(?, ?)";
		executeUpdate(query, new Object[] { phone, aComplex.getId() });
	}

	@Override
	public void updatePhone(Complex aComplex, String oldPhone, String newPhone)
			throws ElementNotExistsException, PersistenceException {

		String query = "UPDATE PHONE set \"phone\" = ? where \"phone_id\" = "
				+ "(SELECT \"phone_id\" from PHONE where \"phone\" = ?" +
						"AND \"complex_id\" = ?)";

		executeUpdate(query, new Object[] { newPhone, oldPhone, aComplex.getId() });
	}
	
	@Override
	public List<String> getPhones(Complex aComplex) {
		String query = "SELECT \"phone\" FROM PHONE WHERE \"complex_id\" = ?";
		return executeQuery(query, new Object[] { aComplex.getId() }, PhoneBuilder
				.getInstance());
	}
}