package com.canchita.DAO.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface QueryProcessor<E> {

	List<E> buildCollection(ResultSet resultSet) throws SQLException;
	
}
