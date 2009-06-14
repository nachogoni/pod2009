package com.canchita.DAO.db;

import java.util.Collection;
import java.util.List;

import com.canchita.DAO.ExpirationDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.ExpirationBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

public class ExpirationDB extends AllDB implements ExpirationDAO {
	private static ExpirationDB instance;

	static {
		instance = new ExpirationDB();
	}

	private ExpirationDB() {
	}

	@FactoryMethod
	public static ExpirationDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Long id) throws PersistenceException {
		String query = "DELETE FROM EXPIRATION_POLICY WHERE \"expiration_policy_id\" = ?";
		executeUpdate(query, new Object[] { id });

	}

	@Override
	public boolean exists(Long id) {
		String query = "SELECT COUNT(*) AS COUNT FROM EXPIRATION_POLICY WHERE"
				+ " \"expiration_policy_id\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { id },
				CountBuilder.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public Collection<Expiration> getAll() {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY ORDER BY \"expiration_policy_id\" ASC";

		List<Expiration> results = executeQuery(query, new Object[] {},
				ExpirationBuilder.getInstance());

		return results;
	}

	@Override
	public Collection<Expiration> getAll(Field field) {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY WHERE \"field_id\" = ? "
				+ "ORDER BY \"expiration_policy_id\" ASC";

		List<Expiration> results = executeQuery(query, new Object[] { field
				.getId() }, ExpirationBuilder.getInstance());

		return results;
	}

	@Override
	public Collection<Expiration> getAll(Complex complex) {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY WHERE \"complex_id\" = ?"
				+ "ORDER BY \"expiration_policy_id\" ASC";

		List<Expiration> results = executeQuery(query, new Object[] { complex
				.getId() }, ExpirationBuilder.getInstance());

		return results;
	}

	@Override
	public Expiration getById(Long id) throws PersistenceException {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY WHERE "
				+ "\"expiration_policy_id\" = ?";

		List<Expiration> results = executeQuery(query, new Object[] { id },
				ExpirationBuilder.getInstance());

		if (results.size() == 0)
			throw new ElementNotExistsException("No existe la política asociada.");
		
		return results.get(0);
	}

	@Override
	public Expiration getByScore(Field field, Integer score)
			throws ElementNotExistsException {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY WHERE \"field_id\" = ? AND "
				+ "\"from_score\" <= ? AND" + "\"to_score\" >= ?"
				+ "ORDER BY \"expiration_policy_id\" ASC";

		List<Expiration> results = executeQuery(query, new Object[] {
				field.getId(), score, score }, ExpirationBuilder.getInstance());

		// Si la lista está vacía implica que la cancha no ha refinado el
		// criterio
		// y se debe devolver el puntaje que asignó el complejo

		if (results.size() == 0)
			return getByScore(field.getComplex(), score);

		return results.get(results.size() - 1);
	}

	@Override
	public Expiration getByScore(Complex complex, Integer score)
			throws ElementNotExistsException {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY WHERE \"complex_id\" = ? AND \"field_id\" "
				+ "IS NULL AND" + "\"from_score\" <= ? AND"
				+ "\"to_score\" >= ?" + "ORDER BY \"expiration_policy_id\" ASC";

		List<Expiration> results = executeQuery(query, new Object[] {
				complex.getId(), score, score }, ExpirationBuilder
				.getInstance());

		if (results.size() == 0)
			throw new ElementNotExistsException(
					"No se encuentra el puntaje asociado");

		return results.get(results.size() - 1);
	}

	@Override
	public void save(Field field, Expiration expiration)
			throws PersistenceException {
		String query = "INSERT into EXPIRATION_POLICY VALUES (NULL, ?, ?, ?, ?, ?, ?)";

		executeUpdate(query, new Object[] { field.getComplex().getId(),
				field.getId(), expiration.getScoreFrom(),
				expiration.getScoreTo(), expiration.getDepositLimit(),
				expiration.getBookingLimit() });

	}

	@Override
	public void save(Complex complex, Expiration expiration)
			throws PersistenceException {
		String query = "INSERT into EXPIRATION_POLICY VALUES (NULL, ?, NULL, ?, ?, ?, ?)";

		executeUpdate(query, new Object[] { complex.getId(),
				expiration.getScoreFrom(), expiration.getScoreTo(),
				expiration.getDepositLimit(), expiration.getBookingLimit() });

	}

	@Override
	public void update(Expiration expiration) throws PersistenceException {
		String query = "UPDATE EXPIRATION_POLICY set \"from_score\" = ?, \"to_score\" = ?, "
				+ "\"days_being_half_signed\" = ?, \"days_being_reserved\" = ?"
				+ " where \"expiration_policy_id\" = ?";

		executeUpdate(query, new Object[] { expiration.getScoreFrom(),
				expiration.getScoreTo(), expiration.getDepositLimit(),
				expiration.getBookingLimit(), expiration.getId() });

	}

	@Override
	public Expiration getDefaultForComplex(Long id)
			throws ElementNotExistsException {
		String query = "SELECT \"expiration_policy_id\", \"from_score\","
				+ "\"to_score\", \"days_being_half_signed\", \"days_being_reserved\""
				+ " FROM EXPIRATION_POLICY WHERE " + "\"complex_id\" = ?"
				+ "ORDER BY \"expiration_policy_id\" ASC";

		List<Expiration> results = executeQuery(query, new Object[] { id },
				ExpirationBuilder.getInstance());

		if (results.size() == 0)
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public void updateDefault(Long complexId, Integer bookingLimit,
			Integer depositLimit) throws PersistenceException {
		Expiration expiration = getDefaultForComplex(complexId);
		expiration.setBookingLimit(bookingLimit);
		expiration.setDepositLimit(depositLimit);
		update(expiration);
	}
}
