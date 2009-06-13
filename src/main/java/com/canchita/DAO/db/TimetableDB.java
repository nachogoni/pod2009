package com.canchita.DAO.db;

import java.util.Collection;
import java.util.List;

import com.canchita.DAO.TimetableDAO;
import com.canchita.DAO.db.builders.AvailabilityBuilder;
import com.canchita.model.complex.Availability;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public class TimetableDB extends AllDB implements TimetableDAO {

	private static TimetableDB instance;

	static {
		instance = new TimetableDB();
	}

	private TimetableDB() {
	}

	public static TimetableDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Long id) {
		String query = "DELETE FROM TIMETABLE WHERE \"timetable_id\" = ?";
		executeUpdate(query, new Object[] { id });
	}

	@Override
	public Collection<Availability> getByComplexId(Long complexId)
			throws ElementNotExistsException {

		String query = "SELECT \"timetable_id\", \"day\", "
				+ "to_char(\"from\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as "
				+ "\"from\", "
				+ "to_char(\"to\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as "
				+ "\"to\" " + "FROM TIMETABLE where \"complex_id\" = ?";

		List<Availability> results = executeQuery(query,
				new Object[] { complexId }, AvailabilityBuilder.getInstance());

		return results;
	}

	@Override
	public Availability getById(Long id) throws ElementNotExistsException {
		String query = "SELECT \"timetable_id\", \"day\", "
				+ "to_char(\"from\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as "
				+ "\"from\", "
				+ "to_char(\"to\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as "
				+ "\"to\" " + "FROM TIMETABLE where \"timetable_id\" = ?";

		List<Availability> results = executeQuery(query, new Object[] { id },
				AvailabilityBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public void save(Availability av, Long complexId)
			throws PersistenceException {

		String a = "TO_TIMESTAMP_TZ('" + av.getSchedule().getStartTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), ";
		String b = "TO_TIMESTAMP_TZ('" + av.getSchedule().getEndTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'))";
		String query = "INSERT INTO TIMETABLE VALUES (NULL, ?, ?, " + a + b;

		executeUpdate(query, new Object[] { complexId, av.getDay().ordinal() });
	}

}
