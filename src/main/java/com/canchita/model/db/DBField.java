package com.canchita.model.db;

public class DBField {
	private String ClassName;
	private String DBName;
	private DbTypes type;
	private boolean PK;
	
	public DBField(String fieldClassName, String fieldDataBaseName, DbTypes fieldType , boolean isPrimaryKey) {
		ClassName = fieldClassName;
		DBName = fieldDataBaseName;
		type = fieldType;
		PK = isPrimaryKey;
	}

	public String getClassName() {
		return this.ClassName;
	}

	public String getDBName() {
		return this.DBName;
	}

	public DbTypes getType() {
		return this.type;
	}

	public boolean isPK() {
		return this.PK;
	}
	
	
}
