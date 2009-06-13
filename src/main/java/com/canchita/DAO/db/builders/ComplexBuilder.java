package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.DAO.db.TimetableDB;
import com.canchita.model.complex.Availability;
import com.canchita.model.complex.Calendar;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementNotExistsException;

public class ComplexBuilder implements QueryProcessor<Complex> {

	private static ComplexBuilder instance;

	static {
		instance = new ComplexBuilder();
	}

	private ComplexBuilder() {
	}

	public static ComplexBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Complex> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Complex> results = new ArrayList<Complex>();
		Complex aComplex;

		while (resultSet.next()) {
			aComplex = this.buildComplex(resultSet);

			results.add(aComplex);
		}

		return results;
	}

	private Complex buildComplex(ResultSet resultSet) throws SQLException {

		Complex aComplex = new Complex(resultSet.getInt("complex_id"),
				resultSet.getString("name"),
				resultSet.getString("description"), resultSet
						.getString("address"), resultSet
						.getString("neighbourhood"), resultSet
						.getString("city"), resultSet.getString("state"),
				resultSet.getString("zip_code"),
				resultSet.getString("country"), resultSet.getString("fax"),
				resultSet.getString("email"), resultSet.getBlob("picture"),
				resultSet.getString("latitude"), resultSet
						.getString("longitude"));

		try {
			Collection<Availability> avs = TimetableDB.getInstance()
					.getByComplexId(aComplex.getId());
			Calendar calendar = new Calendar();

			for (Availability av : avs) {
				calendar.add(av);
			}

			aComplex.setTimeTable(calendar);

		} catch (ElementNotExistsException e) {
			e.printStackTrace();
		}

		return aComplex;
	}
}