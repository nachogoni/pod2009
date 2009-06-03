package com.canchita.model.db;

public enum DbTypes {
	DB_INT, DB_STRING, DB_PK;

	public String toString() {
		if (this == DB_INT) {
			return "int";
		}else if (this == DB_STRING){
			return "String";
		}else{
			return "";
		}
	}
}
