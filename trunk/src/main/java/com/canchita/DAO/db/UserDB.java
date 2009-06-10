package com.canchita.DAO.db;

import java.util.Collection;
import java.util.List;

import com.canchita.DAO.UserDAO;
import com.canchita.DAO.db.builders.AdministratorBuilder;
import com.canchita.DAO.db.builders.CommonUserBuilder;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.EmailBuilder;
import com.canchita.DAO.db.builders.RegisteredBuilder;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Administrator;
import com.canchita.model.user.CommonUser;
import com.canchita.model.user.Registered;

public class UserDB extends AllDB implements UserDAO {

	private static UserDB instance;

	static {
		instance = new UserDB();
	}

	private UserDB() {
		// No hace nada :D
	}

	public static UserDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Registered user) throws ElementNotExistsException {
		String query = "DELETE FROM USERS WHERE \"name\" = ?";

		if (this.exists(user))
			executeUpdate(query, new Object[] { user.getUsername() });
		else
			throw new ElementNotExistsException();
	}

	@Override
	public boolean exists(Registered user) {
		String query = "SELECT COUNT(*) AS COUNT FROM USERS WHERE \"name\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { user
				.getUsername() }, CountBuilder.getInstance());

		return results.get(0) > 0;

	}

	@Override
	public Collection<CommonUser> getAllUsers() {

		String query = "SELECT * FROM USERS WHERE \"is_admin\" = '0'";

		List<CommonUser> results = executeQuery(query,
				new Object[] { }, CommonUserBuilder.getInstance());

		for (CommonUser cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results;
	}

	@Override
	public Collection<Administrator> getAllAdmins() {

		String query = "SELECT * FROM USERS WHERE \"is_admin\" = '1'";

		List<Administrator> results = executeQuery(query,
				new Object[] { }, AdministratorBuilder.getInstance());

		for (Administrator cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results;
	}

	@Override
	public CommonUser getByUserName(String userName)
			throws ElementNotExistsException {

		// CommonUser list gets loaded.
		String query = "SELECT * FROM USERS WHERE \"name\" = ? AND \"is_admin\" = '0'";

		List<CommonUser> results = executeQuery(query,
				new Object[] { userName }, CommonUserBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (CommonUser cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results.get(0);

	}

	public Administrator getByAdminName(String userName)
			throws ElementNotExistsException {

		// CommonUser list gets loaded.
		String query = "SELECT * FROM USERS WHERE \"name\" = ? AND \"is_admin\" = '1'";

		List<Administrator> results = executeQuery(query,
				new Object[] { userName }, AdministratorBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		for (Administrator cu : results) {
			List<String> emails = this.getEmails(cu);

			for (String email : emails) {
				cu.setEmail(email);
			}
		}

		return results.get(0);

	}
	
	@Override
	public List<String> getEmails(Registered user) {
		String query = "SELECT \"email\" FROM EMAIL WHERE \"user_id\" = ?";
		return executeQuery(query, new Object[] { user.getId() }, EmailBuilder
				.getInstance());
	}

	public void save(Registered user) throws PersistenceException {
		String query = "INSERT into USERS VALUES (1, ?, ?, ?, ?, ?)";
		executeUpdate(query, new Object[] { user.getUsername(),
				user.getPassword(), user.getScore(),
				user.getNotifyBeforeExpiration(), user.getIsAdmin() });
	}

	@Override
	public void update(Registered user) throws ElementNotExistsException,
			PersistenceException {

		String query = "UPDATE USERS set \"name\" = ?, \"password\" = ?, "
				+ "\"score\" = ?, \"notify_before_expiration\" = ?, "
				+ "\"is_admin\" = ? where \"user_id\" = ?";

		executeUpdate(query, new Object[] { user.getUsername(),
				user.getPassword(), user.getScore(),
				user.getNotifyBeforeExpiration(), user.getIsAdmin(),
				user.getId() });
	}

	public void updateEmail(Registered user, String oldEmail, String newEmail)
			throws ElementNotExistsException, PersistenceException {

		//TODO: Se podría agregar al último where un user_id = user.getId();
		String query = "UPDATE EMAIL set \"email\" = ? where \"email_id\" = "
				+ "(SELECT \"email_id\" from EMAIL where \"email\" = ?)";

		executeUpdate(query, new Object[] { newEmail, oldEmail });
	}


	@Override
	public void addEmail(Registered user, String email)
			throws ElementNotExistsException, PersistenceException {
		String query = "INSERT INTO EMAIL VALUES(NULL, ?, ?)";
		executeUpdate(query, new Object[] {email, user.getId()});
	}

	@Override
	public Registered login(String username, String password) {
		
		String query = "SELECT * " +
				"FROM users " +
				"WHERE \"name\" = ? AND " +
				"\"password\" = ?";

		//TODO FIJARSE QUE ESE RUNTIME NO ME DEJE VER QUE
		//NO EXISTE EL USUARIO O QUE LE PIFIO A LA PASS
		
		executeQuery(query, new Object[] { "asd" }, EmailBuilder
				.getInstance());
		
		List<Registered> results = executeQuery(query,
				new Object[] { username,password }, RegisteredBuilder.getInstance());
			
		
		return results.get(0);
	}

}