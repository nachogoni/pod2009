package com.canchita.model.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TestDBModel {
	
	static String getTable(){
		return "test2";
	}
	
	static String getObjClass(){
		return "TestDBClass";
	}
	
	static ArrayList<DBField> getFields(){
		ArrayList<DBField> data = new ArrayList<DBField>();
		
		data.add(new DBField("name","nombre", DbTypes.DB_STRING, false));
		data.add(new DBField("hola","lala", DbTypes.DB_STRING, false));
		data.add(new DBField("akaka","lala", DbTypes.DB_INT, true));
		
		return data;
	}
	
	
}
