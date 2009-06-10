package com.canchita.DAO;

import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.PersistenceException;

public interface ScoreSystemDAO {

	public void save(ScoreSystem aScoreSystem) throws PersistenceException;

	public ScoreSystem getById(Long id) throws PersistenceException;

	public void update(Long id, ScoreSystem aScoreSystem)
			throws PersistenceException;

	public void delete(Long id) throws PersistenceException;
}