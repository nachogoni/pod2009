package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.booking.Schedule;
import com.canchita.model.complex.Availability;

public class AvailabilityBuilder implements QueryProcessor<Availability> {

	private static AvailabilityBuilder instance;

	static {
		instance = new AvailabilityBuilder();
	}

	private AvailabilityBuilder() {
	}

	public static AvailabilityBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Availability> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Availability> results = new ArrayList<Availability>();
		Availability av;

		while (resultSet.next()) {

			//Armo el schedule
			DateTime startDate = new DateTime(resultSet.getString("from"));
			DateTime endDate = new DateTime(resultSet.getString("to"));
			Schedule schedule = new Schedule(startDate, endDate);

			//Construyo el booking
			av = new Availability( resultSet.getLong("timetable_id"),
					resultSet.getLong("day"),
					schedule);
			 
			results.add(av);
		}

		return results;
	}
	
}
