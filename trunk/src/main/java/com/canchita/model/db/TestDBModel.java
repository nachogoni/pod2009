package com.canchita.model.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class TestDBModel {
	
	static String getTable(){
		return "TESTTABLE";
	}
	
	static String getObjClass(){
		return "TestDBClass";
	}
	
	static ArrayList<DBField> getFields(){
		ArrayList<DBField> data = new ArrayList<DBField>();
		
		data.add(new DBField("Name","nombre","String", false));
		
		return data;
	}
	
	
}
