package com.canchita.DAO.db;

import java.util.Collection;
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

	@Override
	public Collection<Field> getFiltered(String filter) {
		// TODO Auto-generated method stub
		return null;
	}

}
