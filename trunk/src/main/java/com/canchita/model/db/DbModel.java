package com.canchita.model.db;

import java.util.ArrayList;

public abstract class DbModel {
	/**
	 * Devuelve el nombre de la tabla de la base de datos
	 * 
	 * @return String
	 */
	public abstract String getTable();

	/**
	 * Devuelve el nombre de la clase
	 * 
	 * @return
	 */
	public abstract String getObjClass();
	
	/**
	 * Devuelve el mapeo de datos
	 * 
	 * @return
	 */
	public abstract ArrayList<DBField> getFields();
}
