package com.canchita.DAO;

import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Registered;

public interface RegisterDAO {

	void saveHash(String username, String password, String email, String hash);

	Registered confirmateHash(String hash) throws PersistenceException;

}
