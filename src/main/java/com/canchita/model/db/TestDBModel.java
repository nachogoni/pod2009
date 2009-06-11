package com.canchita.model.db;

import java.util.ArrayList;

public class TestDBModel extends DbModel{
	
	public String getTable(){
		return "test2";
	}
	
	public String getObjClass(){
		return "com.canchita.model.db.TestDBClass";
	}
	
	public ArrayList<DBField> getFields(){
		ArrayList<DBField> data = new ArrayList<DBField>();
		
		data.add(new DBField("name","nombre", DbTypes.DB_STRING, false));
		data.add(new DBField("hola","lala", DbTypes.DB_STRING, false));
		data.add(new DBField("akaka","lala", DbTypes.DB_INT, true));
		
		return data;
	}
	
	
}
