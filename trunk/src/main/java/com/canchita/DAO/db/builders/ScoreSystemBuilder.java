package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.canchita.DAO.db.QueryProcessor;
import com.canchita.model.complex.ScoreSystem;

public class ScoreSystemBuilder implements QueryProcessor<ScoreSystem> {
	private static ScoreSystemBuilder instance;

	static {
		instance = new ScoreSystemBuilder();
	}

	private ScoreSystemBuilder() {
	}

	public static ScoreSystemBuilder getInstance() {
		return instance;
	}

	public List<ScoreSystem> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<ScoreSystem> results = new ArrayList<ScoreSystem>();
		ScoreSystem aScoreSystem;

		while (resultSet.next()) {
			aScoreSystem = this.buildScoreSystem(resultSet);

			results.add(aScoreSystem);
		}

		return results;
	}

	public ScoreSystem buildScoreSystem(ResultSet resultSet)
			throws SQLException {
		return new ScoreSystem(resultSet.getInt("on_reserve"), resultSet
				.getInt("on_half_payment"),
				resultSet.getInt("on_full_payment"), resultSet
						.getInt("drop_reserved"), resultSet
						.getInt("drop_half_paid"));

	}
}
