package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.booking.Expiration;

public class ExpirationBuilder implements QueryProcessor<Expiration> {

	private static ExpirationBuilder instance;

	static {
		instance = new ExpirationBuilder();
	}

	private ExpirationBuilder() {
	}

	public static ExpirationBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Expiration> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Expiration> results = new ArrayList<Expiration>();
		Expiration anExpiration;

		while (resultSet.next()) {

			anExpiration = new Expiration(resultSet
					.getLong("expiration_policy_id"), resultSet
					.getInt("from_score"), resultSet.getInt("to_score"),
					resultSet.getInt("days_being_half_signed"), resultSet
							.getInt("days_being_reserved"));

			results.add(anExpiration);
		}

		return results;
	}
}