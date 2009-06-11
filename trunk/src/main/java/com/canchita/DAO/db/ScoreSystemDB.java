package com.canchita.DAO.db;

import java.util.List;

import com.canchita.DAO.ScoreSystemDAO;
import com.canchita.DAO.db.builders.ScoreSystemBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public class ScoreSystemDB extends AllDB implements ScoreSystemDAO {
	private static ScoreSystemDB instance;

	static {
		instance = new ScoreSystemDB();
	}

	private ScoreSystemDB() {
	}

	@FactoryMethod
	public static ScoreSystemDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Long id) throws PersistenceException {
		String query = "DELETE FROM SCORE_SYSTEM WHERE \"score_system_id\" = ?";
		executeUpdate(query, new Object[] { id });
	}

	@Override
	public ScoreSystem getById(Long id) throws PersistenceException {
		// CommonUser list gets loaded.
		String query = "SELECT * FROM SCORE_SYSTEM WHERE \"score_system_id\" = ?";

		List<ScoreSystem> results = executeQuery(query, new Object[] { id },
				ScoreSystemBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public void save(ScoreSystem scoreSystem) throws PersistenceException {
		String query = "INSERT into SCORE_SYSTEM VALUES (1, ?, ?, ?, ?, ?)";
		executeUpdate(query, new Object[] { scoreSystem.getBooking(),
				scoreSystem.getDeposit(), scoreSystem.getPay(),
				scoreSystem.getDownBooking(), scoreSystem.getDownDeposit() });

	}

	@Override
	public void update(Long id, ScoreSystem scoreSystem)
			throws PersistenceException {
		String query = "UPDATE SCORE_SYSTEM set \"on_reserve\" = ?, \"on_half_payment\" = ?, "
				+ "\"on_full_payment\" = ?, \"drop_reserved\" = ?, "
				+ "\"drop_half_paid\" = ? where \"score_system_id\" = ?";

		executeUpdate(query,
				new Object[] { scoreSystem.getBooking(),
						scoreSystem.getDeposit(), scoreSystem.getPay(),
						scoreSystem.getDownBooking(),
						scoreSystem.getDownDeposit(), id });

	}

	@Override
	public void update(int booking, int deposit, int pay,
			int downBooking, int downDeposit) throws PersistenceException {
		ScoreSystem scoreSystem = new ScoreSystem(booking, deposit, pay,
				downBooking, downDeposit);
		this.update(1L, scoreSystem);

	}
}