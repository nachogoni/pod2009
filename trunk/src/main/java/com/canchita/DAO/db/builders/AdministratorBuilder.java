package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.user.Administrator;

public class AdministratorBuilder implements QueryProcessor<Administrator> {

	private static AdministratorBuilder instance;

	static {
		instance = new AdministratorBuilder();
	}

	private AdministratorBuilder() {
	}

	public static AdministratorBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Administrator> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Administrator> results = new ArrayList<Administrator>();
		Administrator anAdmin;

		while (resultSet.next()) {
			
			anAdmin = this.buildAdministrator(resultSet);
			

			results.add(anAdmin);
		}

		return results;
	}

	public Administrator buildAdministrator(ResultSet resultSet) throws SQLException {
		return new Administrator(resultSet.getLong("user_id"),
				resultSet.getString("name"),
				resultSet.getString("password"));
	}
}
