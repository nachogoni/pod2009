package com.canchita.DAO.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.FieldBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.model.booking.BookingStatus;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

public class FieldDB extends AllDB implements FieldDAO {

	private static FieldDB instance;

	static {
		instance = new FieldDB();
	}

	private FieldDB() {
		// No hace nada :D
	}

	@FactoryMethod
	public static FieldDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Long id) throws ElementNotExistsException {

		String query = "DELETE FROM FIELD WHERE \"field_id\" = ?";
		executeUpdate(query, new Object[] { id });

	}

	@Override
	public boolean exists(Field field) {
		String query = "SELECT COUNT(*) AS COUNT FROM FIELD WHERE \"field_id\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { field
				.getId() }, CountBuilder.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public Collection<Field> getAll() {
		String query = "SELECT * FROM FIELD";

		List<Field> results = executeQuery(query, new Object[] {}, FieldBuilder
				.getInstance());

		// TODO: Agregar cosas de otras tablas, onda scoreSystem and so.
		return results;
	}

	@Override
	public Collection<Field> getByComplex(Long idComplex)
			throws PersistenceException {
		String query = "SELECT * FROM FIELD WHERE \"complex_id\" = ?";

		List<Field> results = executeQuery(query, new Object[] { idComplex },
				FieldBuilder.getInstance());

		return results;
	}

	@Override
	public Field getById(Long id) throws ElementNotExistsException {
		String query = "SELECT * FROM FIELD WHERE \"field_id\" = ?";

		List<Field> results = executeQuery(query, new Object[] { id },
				FieldBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public Collection<Field> getFiltered(String filter) {
		return null;
	}

	@Override
	public Collection<Field> getFiltered(String searchName,
			String searchDescription, String searchMaxPrice,
			String searchNumberOfPlayers, String searchHasRoof,
			String searchFloorType, String searchNeighbourhood,
			String searchTown, String searchState, String searchCountry,
			String searchAddress, DateTime from, DateTime to) {

		List<Field> results = null;
		boolean isSearchingByPlace = false;
		String minHasRoof = "0";
		String maxHasRoof = "1";
		String minType = "0";
		String maxType = String.valueOf(Integer.MAX_VALUE);
		String minPlayers = "0";
		String maxPlayers = String.valueOf(Integer.MAX_VALUE);

		String query = "SELECT DISTINCT FIELD.\"field_id\",FIELD.\"complex_id\","
				+ "FIELD.\"name\", FIELD.\"description\",FIELD.\"number_of_players\","
				+ "FIELD.\"has_roof\",FIELD.\"type\",FIELD.\"price\","
				+ "FIELD.\"under_maintenance\", FIELD.\"accont_percentage\""
				+ " FROM FIELD,TIMETABLE,COMPLEX "
				+ "WHERE 1=1 AND COMPLEX.\"complex_id\" = TIMETABLE.\"complex_id\" "
				+ "AND FIELD.\"complex_id\" = COMPLEX.\"complex_id\" "
				+ "AND lower(FIELD.\"name\") LIKE lower(?) AND ";

		if (searchName == null)
			searchName = "%";
		else
			searchName = "%" + searchName + "%";

		if (searchNeighbourhood == null || searchNeighbourhood == "")
			searchNeighbourhood = "%";
		else {
			isSearchingByPlace = true;
			searchNeighbourhood = "%" + searchNeighbourhood + "%";
		}

		if (searchTown == null || searchTown == "")
			searchTown = "%";
		else {
			isSearchingByPlace = true;
			searchTown = "%" + searchTown + "%";
		}

		if (searchState == null || searchState == "")
			searchState = "%";
		else {
			isSearchingByPlace = true;
			searchState = "%" + searchState + "%";
		}

		if (searchCountry == null || searchCountry == "")
			searchCountry = "%";
		else {
			isSearchingByPlace = true;
			searchCountry = "%" + searchCountry + "%";
		}

		if (searchAddress == null || searchAddress == "")
			searchAddress = "%";
		else {
			isSearchingByPlace = true;
			searchAddress = "%" + searchAddress + "%";
		}

		if (searchDescription == null || searchDescription == "") {
			query += "(FIELD.\"description\" IS NULL OR lower(FIELD.\"description\") LIKE lower(?) )";
			searchDescription = "%";
		} else {
			query += "lower(FIELD.\"description\") LIKE lower(?)";
			searchDescription = "%" + searchDescription + "%";
		}

		query += " AND CAST(\"price\" AS FLOAT) <= ? AND \"has_roof\" <= ? AND \"has_roof\" >= ? "
				+ "AND \"type\" <= ? AND \"type\" >= ?"
				+ " AND \"number_of_players\" <= ? AND \"number_of_players\" >= ?";

		if (searchMaxPrice == null || searchMaxPrice == "")
			searchMaxPrice = String.valueOf(Integer.MAX_VALUE);

		if (searchHasRoof != null && searchHasRoof != "") {
			minHasRoof = searchHasRoof.equals("yes") ? "1" : "0";
			maxHasRoof = searchHasRoof.equals("yes") ? "1" : "0";
		}

		if (searchFloorType != null && searchFloorType != "") {
			minType = searchFloorType;
			maxType = searchFloorType;
		}

		if (searchNumberOfPlayers != null && searchNumberOfPlayers != "") {
			minPlayers = searchNumberOfPlayers;
			maxPlayers = searchNumberOfPlayers;
		}

		List<Object> params = new ArrayList<Object>();

		params.add(searchName);
		params.add(searchDescription);
		params.add(searchMaxPrice);
		params.add(maxHasRoof);
		params.add(minHasRoof);
		params.add(maxType);
		params.add(minType);
		params.add(maxPlayers);
		params.add(minPlayers);

		if (from != null && to != null) {

			String sqlDateFrom = "to_date (to_char ( TO_TIMESTAMP_TZ('"
					+ from
					+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";

			String sqlDateTo = "to_date (to_char ( TO_TIMESTAMP_TZ('"
					+ to
					+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";

			query += " AND ( \"field_id\" NOT IN "
					+ "( SELECT DISTINCT \"field_id\" FROM RESERVATION WHERE ( \"state\" = ? OR \"state\" = ?) AND ( "
					+ sqlDateFrom
					+ " < to_date (to_char (\"end_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS') "
					+ "AND "
					+ sqlDateTo
					+ " > to_date (to_char (\"start_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS') ) ) ) ";

			params.add(BookingStatus.BOOKED.getIndex());
			params.add(BookingStatus.HALF_PAID.getIndex());

			String fromTS = "( TO_TIMESTAMP_TZ('" + from
					+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'))";
			String toTS = "( TO_TIMESTAMP_TZ('" + to
					+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'))";

			String sqlFromHour = "EXTRACT( HOUR FROM" + fromTS + ") ";
			String sqlFromMinute = "EXTRACT( MINUTE FROM" + fromTS + ") ";
			String sqlToHour = "EXTRACT( HOUR FROM" + toTS + ") ";
			String sqlToMinute = "EXTRACT( MINUTE FROM" + toTS + ") ";

			query += " AND ( mod(to_char(" + fromTS
					+ ",'D') + 5,7) = \"day\" AND ( ( " + sqlFromHour
					+ "< EXTRACT(HOUR from \"to\") OR ( " + sqlFromHour
					+ "= EXTRACT(HOUR from \"to\") AND " + sqlFromMinute
					+ "< EXTRACT(MINUTE from \"to\") ) ) " + " AND ( "
					+ sqlToHour + "> EXTRACT(HOUR from \"from\") OR ( "
					+ sqlToHour + "= EXTRACT(HOUR from \"from\") AND "
					+ sqlToMinute + "> EXTRACT(MINUTE from \"from\") ) ) ) ) ";

		}

		if (isSearchingByPlace) {
			query += " AND FIELD.\"complex_id\" IN ( SELECT \"complex_id\" FROM COMPLEX"
					+ " WHERE lower(\"address\") LIKE lower(?) AND lower(\"neighbourhood\") LIKE lower(?) AND lower(\"city\") LIKE lower(?) "
					+ "AND lower(\"state\") LIKE lower(?) AND lower(\"country\") LIKE lower(?) )";

			params.add(searchAddress);
			params.add(searchNeighbourhood);
			params.add(searchTown);
			params.add(searchState);
			params.add(searchCountry);

		}

		try {
			results = executeQuery(query, params.toArray(), FieldBuilder
					.getInstance());
		} catch (RuntimeException r) {
			r.getCause().printStackTrace();
		}

		return results;
	}

	@Override
	public Collection<Field> getLastFields(String province, String locality,
			String neighbourhood, Long listCount) {

		String query = "SELECT * FROM FIELD, COMPLEX WHERE "
				+ "FIELD.\"complex_id\" = COMPLEX.\"complex_id\" AND "
				+ " lower(\"state\") LIKE lower(?) AND lower(\"city\") LIKE lower(?) AND "
				+ "lower(\"neighbourhood\") LIKE lower(?) AND rownum <= ? ORDER BY \"field_id\"";

		if (province == null || province.equals("")) {
			province = "%";
		}

		if (locality == null || locality.equals("")) {
			locality = "%";
		}

		if (neighbourhood == null || neighbourhood.equals("")) {
			neighbourhood = "%";
		}

		List<Field> results = executeQuery(query, new Object[] { province,
				locality, neighbourhood, listCount }, FieldBuilder
				.getInstance());

		return results;
	}

	@Override
	public void save(Field field) throws PersistenceException {
		String query = "INSERT into FIELD VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			executeUpdate(query, new Object[] { field.getComplex().getId(),
					field.getName(), field.getDescription(),
					field.getNumberOfPlayers(), bool2Long(field.isHasRoof()),
					field.getFloor().ordinal(), field.getPrice(),
					bool2Long(field.isUnder_maintenance()),
					field.getAccontationPercentage().toString() });

		} catch (RuntimeException re) {
			Throwable sql = re.getCause();

			if (sql instanceof SQLException
					&& sql.getMessage().contains("FIELD_UNIQUE")) {
				throw new ElementExistsException(
						"Ya existe una cancha con ese nombre en este complejo");
			} else {
				throw re;
			}

		}
	}

	private Long bool2Long(boolean b) {
		if (b)
			return 1L;

		return 0L;
	}

	@Override
	public void update(Field field) throws ElementNotExistsException,
			PersistenceException {

		if (!exists(field))
			throw new ElementNotExistsException();

		String query = "UPDATE FIELD set \"complex_id\" = ?, \"name\" = ?, "
				+ "\"description\" = ?, \"number_of_players\" = ?, "
				+ "\"has_roof\" = ?, \"type\" = ?, \"price\" = ?,"
				+ "\"under_maintenance\" = ?, \"accont_percentage\" = ? "
				+ "where \"field_id\" = ?";
		try {
			executeUpdate(query, new Object[] { field.getComplex().getId(),
					field.getName(), field.getDescription(),
					field.getNumberOfPlayers(), bool2Long(field.isHasRoof()),
					field.getFloor().ordinal(), field.getPrice(),
					bool2Long(field.isUnder_maintenance()),
					field.getAccontationPercentage(), field.getId() });

		} catch (RuntimeException re) {
			Throwable sql = re.getCause();

			if (sql instanceof SQLException
					&& sql.getMessage().contains("FIELD_UNIQUE")) {
				throw new ElementExistsException(
						"Ya existe una cancha con ese nombre en este complejo");
			} else {
				throw re;
			}

		}

	}

}
