package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.user.CommonUser;

public class CommonUserBuilder implements QueryProcessor<CommonUser> {

	private static CommonUserBuilder instance;

	static {
		instance = new CommonUserBuilder();
	}

	private CommonUserBuilder() {
	}

	public static CommonUserBuilder getInstance() {
		return instance;
	}

	@Override
	public List<CommonUser> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<CommonUser> results = new ArrayList<CommonUser>();
		CommonUser aUser;

		while (resultSet.next()) {
			aUser = new CommonUser(resultSet.getLong("user_id"),
					resultSet.getString("name"),
					resultSet.getString("password"),
					resultSet.getString("email"),
					resultSet.getLong("score"),
					resultSet.getLong("notify_before_expiration")
					);

			results.add(aUser);
		}

		return results;
	}
}
