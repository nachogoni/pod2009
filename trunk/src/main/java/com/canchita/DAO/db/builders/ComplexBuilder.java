package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.complex.Complex;

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

		return new Complex(resultSet.getInt("complex_id"), resultSet
				.getString("name"), resultSet.getString("description"),
				resultSet.getString("address"), resultSet.getString("city"),
				resultSet.getString("state"), resultSet.getString("country"),
				resultSet.getString("fax"), resultSet.getString("email"),
				resultSet.getBlob("picture"), resultSet.getString("latitude"),
				resultSet.getString("longitude"));
	}
}