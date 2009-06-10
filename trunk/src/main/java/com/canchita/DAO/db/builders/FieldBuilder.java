package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.field.Field;

public class FieldBuilder implements QueryProcessor<Field> {

	private static FieldBuilder instance;

	static {
		instance = new FieldBuilder();
	}

	private FieldBuilder() {
	}

	public static FieldBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Field> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Field> results = new ArrayList<Field>();
		Field aField;

		while (resultSet.next()) {
			
			aField = new Field(resultSet.getLong("field_id"),
					resultSet.getLong("complex_id"),
					resultSet.getString("name"),
					resultSet.getString("description"),
					resultSet.getLong("number_of_players"),
					resultSet.getBoolean("has_roof"),
					resultSet.getLong("type"),
					resultSet.getFloat("price"),
					resultSet.getBlob("picture"),
					resultSet.getBoolean("under_maintenance")
					);

			
			results.add(aField);
		}

		return results;
	}
}
