package com.canchita.DAO.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.canchita.DAO.ComplexDAO;
import com.canchita.DAO.db.builders.ComplexBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.PhoneBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.jdbc.ConnectionManager;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.location.Place;

public class ComplexDB extends AllDB implements ComplexDAO {
	private static ComplexDB instance;

	static {
		instance = new ComplexDB();
	}

	private ComplexDB() {
	}

	@FactoryMethod
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

			//Agrego Calendar.
			complex.setTimeTable(getCalendar(complex));
		}

		

		return results;
	}

	@Override
	public Collection<String> getNeighbourhoods() {
		String query = "SELECT DISTINCT \"neighbourhood\" FROM COMPLEX ORDER BY \"neighbourhood\"";

		ConnectionManager connectionManager = connectionPool.getConnectionManager();
		Connection connection = connectionManager.getConnection();

		Collection<String> result = null;
		
	    try
	    {
	      Statement st = connection.createStatement();
	      ResultSet rs = st.executeQuery(query);
	      
	      result = new ArrayList<String>();
	      
	      while (rs.next()) {
	    	  result.add(rs.getString("neighbourhood"));
	      }
	      
	    }
	    catch (SQLException ex)
	    {
	      System.err.println(ex.getMessage());
		} finally {
			connectionPool.releaseConnectionManager(connectionManager);
		}

		return result;
		
	}
	
	private Calendar getCalendar(Complex aComplex) {
		Calendar aCalendar = new Calendar();
		Collection<Availability> avs = null;
		try {
			avs = TimetableDB.getInstance().getByComplexId(aComplex.getId());
		} catch (ElementNotExistsException e) {
			return null;
		}

		for ( Availability av : avs ) {
			aCalendar.add(av);
		}

		return aCalendar;
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

			//Agrego Calendar.
			complex.setTimeTable(getCalendar(complex));
		}

		return results.get(0);
	}

	@Override
	public void save(Complex complex) throws PersistenceException {
		String query = "INSERT into COMPLEX VALUES (NULL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			executeUpdate(query, new Object[] { complex.getName(),
					complex.getDescription(), complex.getPlace().getAddress(),
					complex.getPlace().getNeighbourhood(),
					complex.getPlace().getTown(), complex.getPlace().getState(),
					complex.getPlace().getZipCode(),
					complex.getPlace().getCountry(), complex.getFax(),
					complex.getEmail(), complex.getPicture(),
					complex.getPlace().getLatitude(),
					complex.getPlace().getLongitude() });
		}
		catch(RuntimeException re) {
			Throwable sql = re.getCause();

			if (sql instanceof SQLException
					&& sql.getMessage().contains("PLACE_UNIQUE")) {
				throw new ElementExistsException(
						"Ya existe un complejo con esa ubicación");
			} else {
				throw re;
			}
		}

	}

	@Override
	public void update(Complex complex) throws PersistenceException {
		String query = "UPDATE COMPLEX set \"name\" = ?, \"description\" = ?, "
				+ "\"address\" = ?, \"neighbourhood\" = ?, \"city\" = ?, "
				+ "\"state\" = ?, \"zip_code\" = ?, \"country\" = ?, \"fax\" = ?,"
				+ "\"email\" = ?, \"picture\" = ?, \"latitude\" = ?, \"longitude\" = ?"
				+ "where \"complex_id\" = ?";
		
		try{
			executeUpdate(query, new Object[] { complex.getName(),
					complex.getDescription(), complex.getPlace().getAddress(),
					complex.getPlace().getNeighbourhood(),
					complex.getPlace().getTown(), complex.getPlace().getState(),
					complex.getPlace().getZipCode(),
					complex.getPlace().getCountry(), complex.getFax(),
					complex.getEmail(), complex.getPicture(),
					complex.getPlace().getLatitude(),
					complex.getPlace().getLongitude(), complex.getId() });
		}
		catch(RuntimeException re) {
			Throwable sql = re.getCause();
	
			if (sql instanceof SQLException
					&& sql.getMessage().contains("PLACE_UNIQUE")) {
				throw new ElementExistsException(
						"Ya existe una complejo con esa ubicación");
			} else {
				throw re;
			}
		}
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

			//Agrego Calendar.
			complex.setTimeTable(getCalendar(complex));
		}

		return results;
	}

	@Override
	public void deletePhones(Complex aComplex)throws ElementNotExistsException, PersistenceException {
		
		String query = "DELETE FROM PHONE WHERE \"complex_id\" = ?";
		executeUpdate(query, new Object[] { aComplex.getId() });
	}
	@Override
	public void addPhone(Complex aComplex, String phone)
			throws ElementNotExistsException, PersistenceException {

		String query = "DELETE FROM PHONE WHERE \"phone\" = ? and \"complex_id\" = ?";
		executeUpdate(query, new Object[] { phone, aComplex.getId() });
		
		query = "INSERT INTO PHONE(\"phone\",\"complex_id\") VALUES(?, ?)";
		executeUpdate(query, new Object[] { phone, aComplex.getId() });
	}

	@Override
	public List<String> getPhones(Complex aComplex) {
		String query = "SELECT \"phone\" FROM PHONE WHERE \"complex_id\" = ?";
		return executeQuery(query, new Object[] { aComplex.getId() },
				PhoneBuilder.getInstance());
	}

	@Override
	public Complex getByPlace(Place place) throws ElementNotExistsException {
		String query = "SELECT * FROM COMPLEX WHERE \"address\" = ? AND "
				+ "\"city\" = ? AND \"state\" = ? AND \"country\" = ?";

		List<Complex> results = executeQuery(query, new Object[] {
				place.getAddress(), place.getTown(), place.getState(),
				place.getCountry() }, ComplexBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (Complex complex : results) {
			List<String> phones = this.getPhones(complex);

			for (String phone : phones) {
				complex.setPhone(phone);
			}

			//Agrego Calendar.
			complex.setTimeTable(getCalendar(complex));
		}

		return results.get(0);
	}
}
