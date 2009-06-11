package com.canchita.service;

import com.canchita.model.complex.ScoreSystem;
import com.canchita.model.exception.PersistenceException;

public interface ScoreSystemServiceProtocol {
	ScoreSystem getScoreSystem() throws PersistenceException;

	void updateScoreSystem(int booking, int deposit, int pay, int downBooking,
			int downDeposit) throws PersistenceException;

}
