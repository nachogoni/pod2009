package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.user.RegistrationInfo;

public class RegistrationInfoBuilder implements QueryProcessor<RegistrationInfo> {

	private static RegistrationInfoBuilder instance;

	static {
		instance = new RegistrationInfoBuilder();
	}

	private RegistrationInfoBuilder() {
	}

	public static RegistrationInfoBuilder getInstance() {
		return instance;
	}

	@Override
	public List<RegistrationInfo> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<RegistrationInfo> results = new ArrayList<RegistrationInfo>();
		RegistrationInfo info;

		while (resultSet.next()) {
			
			info = this.buildRegistrationInfo(resultSet);

			results.add(info);
		}

		return results;
	}

	private RegistrationInfo buildRegistrationInfo(ResultSet resultSet) throws SQLException {
		return new RegistrationInfo(
				resultSet.getString("name"),
				resultSet.getString("password"),
				resultSet.getString("email"),
				resultSet.getBoolean("is_admin")
		);
	}

}
