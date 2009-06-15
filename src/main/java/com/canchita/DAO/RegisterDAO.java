package com.canchita.DAO;

import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.user.Registered;

public interface RegisterDAO {

	void saveHash(String username, String password, String email, String hash) throws ElementExistsException;

	Registered confirmateHash(String hash) throws PersistenceException;

}
