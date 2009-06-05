package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;

public class EmailBuilder implements QueryProcessor<String> {

	private static EmailBuilder instance;

	static {
		instance = new EmailBuilder();
	}

	private EmailBuilder() {
		
	}

	public static EmailBuilder getInstance() {
		return instance;
	}

	public List<String> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<String> results = new ArrayList<String>();

		while (resultSet.next()) {
			results.add(resultSet.getString("email"));
		}

		return results;
	}

}
