package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;

public class CountBuilder implements QueryProcessor<Integer> {

	private static CountBuilder instance;

	static {
		instance = new CountBuilder();
	}

	private CountBuilder() {
		
	}

	public static CountBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Integer> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Integer> results = new ArrayList<Integer>();
		resultSet.next();
		results.add(resultSet.getInt("COUNT"));

		return results;
	}

}
