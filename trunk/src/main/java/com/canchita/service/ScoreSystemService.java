package com.canchita.service;

import com.canchita.DAO.ScoreSystemDAO;
import com.canchita.DAO.factory.DAOFactory;
import com.canchita.DAO.factory.DAOFactory.DAO;
import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.PersistenceException;

public class ScoreSystemService implements ScoreSystemServiceProtocol {

	@Override
	public ScoreSystem getScoreSystem() throws PersistenceException {
		ScoreSystemDAO scoreDAO = DAOFactory.get(DAO.SCORE_SYSTEM);
		// el score system es unico, pero en un futuro...
		ScoreSystem scoreSystem = scoreDAO.getById(1L);
		return scoreSystem;

	}

	@Override
	public void updateScoreSystem(int booking, int deposit, int pay,
			int downBooking, int downDeposit) throws PersistenceException {
		ScoreSystemDAO scoreDAO = DAOFactory.get(DAO.SCORE_SYSTEM);
		scoreDAO.update(booking, deposit, pay, downBooking, downDeposit);

	}
}
