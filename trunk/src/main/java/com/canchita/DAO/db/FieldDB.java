package com.canchita.DAO.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.FieldBuilder;
import com.canchita.DAO.factory.FactoryMethod;
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
			String searchFloorType) {

		String minHasRoof = "0";
		String maxHasRoof = "1";
		String minType = "0";
		String maxType = String.valueOf(Integer.MAX_VALUE);
		String minPlayers = "0";
		String maxPlayers = String.valueOf(Integer.MAX_VALUE);

		String query = "SELECT * FROM FIELD WHERE 1=1 AND \"name\" LIKE ? AND ";

		if (searchName == null)
			searchName = "%";
		else
			searchName = "%" + searchName + "%";
		
		if (searchDescription == null || searchDescription == "") {
			query += "(\"description\" IS NULL OR \"description\" LIKE ? )";
			searchDescription = "%";
		} else {
			query += "\"description\" LIKE ?";
			searchDescription = "%" + searchDescription + "%";
		}

		query += " AND CAST(\"price\" AS FLOAT) <= ? AND \"has_roof\" <= ? AND \"has_roof\" >= ? "
				+ "AND \"type\" <= ? AND \"type\" >= ?"
				+ " AND \"number_of_players\" <= ? AND \"number_of_players\" >= ?";

		if (searchMaxPrice == null || searchMaxPrice == "")
			searchMaxPrice = String.valueOf(Integer.MAX_VALUE);

		if (searchHasRoof != null) {
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

		List<Field> results = executeQuery(query, new Object[] { searchName,
				searchDescription, searchMaxPrice, maxHasRoof, minHasRoof,
				maxType, minType, maxPlayers, minPlayers }, FieldBuilder
				.getInstance());

		return results;
	}

	@Override
	public Collection<Field> getLastFields(String province, String locality, String neighbourhood, Long listCount) {

		String query = "SELECT * FROM FIELD, COMPLEX WHERE "
				+ "FIELD.\"complex_id\" = COMPLEX.\"complex_id\" AND "
				+ " \"state\" LIKE ? AND \"city\" LIKE ? AND "
				+ "\"neighbourhood\" LIKE ? AND rownum <= ? ORDER BY \"field_id\"";
	  		
		if (province == null || province.equals("")) {
			province = "%";
		}
	
		if (locality == null || locality.equals("")) {
			locality = "%";
		}
	
		if (neighbourhood == null || neighbourhood.equals("")) {
			neighbourhood = "%";
		}

		List<Field> results = executeQuery(query, new Object[] { province, locality,
				neighbourhood, listCount }, FieldBuilder.getInstance());

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
					bool2Long(field.isUnder_maintenance()), field.getPicture() });
		}
		catch (RuntimeException re) {
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
				+ "\"under_maintenance\" = ?, \"picture\" = ?"
				+ "where \"field_id\" = ?";
		try{
			executeUpdate(query, new Object[] { field.getComplex().getId(),
					field.getName(), field.getDescription(),
					field.getNumberOfPlayers(), bool2Long(field.isHasRoof()),
					field.getFloor().ordinal(), field.getPrice(),
					bool2Long(field.isUnder_maintenance()), field.getPicture(),
					field.getId() });
		}catch (RuntimeException re) {
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
