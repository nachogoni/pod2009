package com.canchita.DAO.db;

import java.util.List;

import com.canchita.DAO.RegisterDAO;
import com.canchita.DAO.UserDAO;
import com.canchita.DAO.db.builders.RegistrationInfoBuilder;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Registered;
import com.canchita.model.user.RegistrationInfo;

public class RegisterDB extends AllDB implements RegisterDAO {

	private static final RegisterDB INSTANCE = new RegisterDB();

	private RegisterDB() {

	}

	@FactoryMethod
	public static RegisterDAO getInstance() {
		return INSTANCE;
	}

	@Override
	public void saveHash(String username, String password, String email,
			String hash) {

		String query = "INSERT into REGISTER(\"name\",\"password\",\"email\",\"is_admin\",\"hash\") VALUES(?, ?, ?, ?, ?)";
		executeUpdate(query, new Object[] { username, password, email, false,
				hash });

	}

	@Override
	public Registered confirmateHash(String hash) throws PersistenceException {

		String query = "SELECT * FROM REGISTER WHERE \"hash\" = ?";

		List<RegistrationInfo> results = executeQuery(query,
				new Object[] { hash }, RegistrationInfoBuilder.getInstance());

		if (results.isEmpty()) {
			return null;
		}

		this.deleteHash(hash);

		return this.register(results.get(0));
	}

	private void deleteHash(String hash) {
		String query = "DELETE FROM REGISTER WHERE \"hash\" = ?";
		executeUpdate(query, new Object[] { hash });
	}

	private Registered register(RegistrationInfo info)
			throws PersistenceException {

		UserDAO userDAO = DAOFactory.get(DAO.USER);

		return userDAO.save(info.getUsername(), info.getPassword(), info
				.getPassword(), false, info.isAdmin());

	}

}
