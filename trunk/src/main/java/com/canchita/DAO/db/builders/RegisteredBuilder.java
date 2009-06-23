package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.user.Registered;

public class RegisteredBuilder implements QueryProcessor<Registered> {

	private static RegisteredBuilder instance;

	static {
		instance = new RegisteredBuilder();
	}

	private RegisteredBuilder() {
	}

	public static RegisteredBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Registered> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Registered> results = new ArrayList<Registered>();
		Registered aUser;

		while (resultSet.next()) {
			
			if( resultSet.getBoolean("is_admin") ) {
				aUser = AdministratorBuilder.getInstance().buildAdministrator(resultSet);
			}
			else {
				aUser = CommonUserBuilder.getInstance().buildCommonUser(resultSet);
			}

			results.add(aUser);
		}

		return results;
	}
}
