package com.canchita.model.db;

import java.util.ArrayList;

public class ScoreSystemDbModel extends DbModel {

	@Override
	public ArrayList<DBField> getFields() {
		ArrayList<DBField> data = new ArrayList<DBField>();
		
		data.add(new DBField(null,"score_system_id", DbTypes.DB_PK, true));
		data.add(new DBField("booking","on_reserve", DbTypes.DB_INT, false));
		data.add(new DBField("deposit","on_half_payment", DbTypes.DB_INT, false));
		data.add(new DBField("pay","on_full_payment", DbTypes.DB_INT, false));
		data.add(new DBField("downBooking","drop_reserved", DbTypes.DB_INT, false));
		data.add(new DBField("downDeposit","drop_half_paid", DbTypes.DB_INT, false));
	
		return data;
	}

	@Override
	public String getObjClass() {
		return "com.canchita.model.complex.ScoreSystem";
	}

	@Override
	public String getTable() {
		return "SCORE_SYSTEM2";
	}
}
