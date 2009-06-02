package com.canchita.model.db;

public class DBField {
	private String ClassName;
	private String DBName;
	private String type;
	private boolean PK;
	
	public DBField(String FieldClassName, String FieldDataBaseName, String FieldType, boolean isPrimaryKey) {
		ClassName = FieldClassName;
		DBName = FieldDataBaseName;
		type = FieldType;
		PK = isPrimaryKey;
	}

	public String getClassName() {
		return ClassName;
	}

	public String getDBName() {
		return DBName;
	}

	public String getType() {
		return type;
	}

	public boolean isPK() {
		return PK;
	}
	
	
}
