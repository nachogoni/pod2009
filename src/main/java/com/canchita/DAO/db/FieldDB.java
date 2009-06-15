package com.canchita.DAO.db;

import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import com.canchita.DAO.FieldDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.FieldBuilder;
import com.canchita.DAO.factory.FactoryMethod;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Field> getFiltered(String searchName,
			String searchDescription, String searchMaxPrice,
			String searchNumberOfPlayers, String searchHasRoof,
			String searchFloorType) {

		String minHasRoof = "0";
		String maxHasRoof = "1";
		String minType = "1";
		String maxType = String.valueOf(Integer.MAX_VALUE);
		String minPlayers = "0";
		String maxPlayers = String.valueOf(Integer.MAX_VALUE);

		String query = "SELECT * FROM FIELD WHERE 1=1 AND \"name\" LIKE ? AND "
				+ "COALESCE(\"description\", ' ') LIKE ? AND \"price\" <= ? AND "
				+ "COALESCE(\"has_roof\", 0)"
				+ " <= ? AND COALESCE(\"has_roof\", 0) >= ? "
				+ "AND \"type\" <= ? AND \"type\" >= ?"
				+ " AND \"number_of_players\" <= ? AND \"number_of_players\" >= ?";

		if (searchName == null)
			searchName = "%";
		else
			searchName = "%" + searchName + "%";

		if (searchDescription == null)
			searchDescription = "%";
		else
			searchDescription = "%" + searchDescription + "%";

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
	public Collection<Field> getLastFields(String neighbourhood, Long listCount) {

		String query = "SELECT * FROM FIELD, COMPLEX WHERE "
				+ "FIELD.\"complex_id\" = COMPLEX.\"complex_id\" AND "
				+ "\"neighbourhood\" LIKE ? AND rownum <= ? ORDER BY \"field_id\"";

		if (neighbourhood == null || neighbourhood.equals("")) {
			neighbourhood = "%";
		}/*
		 * else { neighbourhood = "%" + neighbourhood + "%"; }
		 */

		List<Field> results = executeQuery(query, new Object[] { neighbourhood,
				listCount }, FieldBuilder.getInstance());

		return results;
	}

	/*
	 * @Override public Collection<Field> getFiltered(Dictionary<String, String>
	 * like, Dictionary<String, String> moreThan, Dictionary<String, String>
	 * lessThan, Dictionary<String, String> equal, List<String> orderedBy) {
	 * 
	 * String key = null, searchString = null; Object[] params = new Object[
	 * ((like == null)?0:like.size()) + ((moreThan == null)?0:moreThan.size())
	 * +((lessThan == null)?0:lessThan.size()) + ((equal ==
	 * null)?0:equal.size()) + ((orderedBy == null)?0:orderedBy.size())]; int
	 * count = 0;
	 * 
	 * StringBuffer query = new StringBuffer("SELECT * FROM FIELD WHERE 1=1");
	 * 
	 * 
	 * for (Enumeration<String> i = like.keys(); i.nextElement() != null;) { key
	 * = i.nextElement(); searchString = like.get(key); query.append(" AND \"" +
	 * key + "\" LIKE ? "); params[count++] = "%" + searchString + "%"; }
	 * 
	 * for (Enumeration<String> i = equal.keys(); i.nextElement() != null;) {
	 * key = i.nextElement(); searchString = equal.get(key);
	 * query.append(" AND \"" + key + "\" = ? "); params[count++] =
	 * searchString; }
	 * 
	 * for (Enumeration<String> i = moreThan.keys(); i.nextElement() != null;) {
	 * key = i.nextElement(); searchString = moreThan.get(key);
	 * query.append(" AND \"" + key + "\" > ? "); params[count++] =
	 * searchString; }
	 * 
	 * for (Enumeration<String> i = lessThan.keys(); i.nextElement() != null;) {
	 * key = i.nextElement(); searchString = lessThan.get(key);
	 * query.append(" AND \"" + key + "\" < ? "); params[count++] =
	 * searchString; }
	 * 
	 * 
	 * 
	 * for (Enumeration<String> i = orderedBy.keys(); i.nextElement() != null;)
	 * { key = i.nextElement(); searchString = orderedBy.get(key);
	 * query.append(" AND \"" + key + "\" LIKE ? "); params[count++] = "%" +
	 * searchString + "%"; }
	 * 
	 * List<Field> results = executeQuery(query.toString(), params,
	 * FieldBuilder.getInstance());
	 * 
	 * return results; }
	 */

	@Override
	public void save(Field field) throws PersistenceException {
		String query = "INSERT into FIELD VALUES (1, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		executeUpdate(query, new Object[] { field.getComplex().getId(),
				field.getName(), field.getDescription(),
				field.getNumberOfPlayers(), bool2Long(field.isHasRoof()),
				field.getFloor().ordinal(), field.getPrice(),
				bool2Long(field.isUnder_maintenance()), field.getPicture() });
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

		executeUpdate(query, new Object[] { field.getComplex().getId(),
				field.getName(), field.getDescription(),
				field.getNumberOfPlayers(), bool2Long(field.isHasRoof()),
				field.getFloor().ordinal(), field.getPrice(),
				bool2Long(field.isUnder_maintenance()), field.getPicture(),
				field.getId() });

	}

}