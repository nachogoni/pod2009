package com.canchita.model.db;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

public class DataBaseConnection {
	private Connection conexion = null;
	private Statement stmt = null;
	private ResultSet rCursor = null;
	Logger logger = Logger.getLogger(DataBaseConnection.class.getName());

	public DataBaseConnection() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Abre la conexion a la DB
	 * 
	 */
	public boolean open() {
		boolean ret = true;

		// Si la conexion no fue abierta
		if (this.conexion == null) {
			try {
				// Leemos el driver de Oracle
				Class.forName("oracle.jdbc.driver.OracleDriver");

				// Nos conectamos a la BD local
				conexion = DriverManager.getConnection(
						"jdbc:oracle:thin:@localhost:1521:itba", "dgomezvi",
						"tppod");

			} catch (ClassNotFoundException e1) {
				// Error si no puedo leer el driver de Oracle
				logger.error("ERROR:No encuentro el driver de la BD: "
						+ e1.getMessage());
				ret = false;
			} catch (SQLException e2) {
				// Error SQL: login/passwd mal
				logger.error("ERROR:Fallo en SQL: " + e2.getMessage());
				ret = false;
			}
		}

		return ret;
	}

	public ResultSet executeQuery(String sSql) {
		ResultSet ret = null;

		if (this.conexion != null) {
			try {
				stmt = conexion.createStatement();

				ret = stmt.executeQuery(sSql);

			} catch (Exception e) {
				logger.error("Error al ejecutar la sentencia: " + sSql);
			}
		}

		return ret;
	}

	public void endQuery() {
		try {
			stmt.close();
		} catch (SQLException e) {
			logger.error("Error al finalizar el statement");
		}
	}

	/**
	 * Ejecuta una sentencia SQL
	 * 
	 * @param sSql
	 * @return
	 */
	public boolean execute(String sSql) {
		boolean ret;

		try {
			/* creo el statement */
			stmt = conexion.createStatement();

			ret = stmt.execute(sSql);

			stmt.close();
		} catch (Exception e) {
			logger.error("Error al ejecutar la sentencia: " + sSql);
			ret = false;
		}

		return ret;
	}

	public boolean isOpened() {
		return conexion != null;
	}

	/**
	 * Cierra la conexion a la db
	 */
	public void close() {
		if (conexion != null) {
			try {
				conexion.close();
				conexion = null;
			} catch (SQLException e) {
				logger.error("Error al cerrar la conexion");
			}

		}
	}

	public Object loadInstance(DbModel model, String pk) {
		ArrayList<String> aPk = new ArrayList<String>();
    	aPk.add(pk);
    	
    	return loadInstance(model, aPk);
	}
	
	public Object loadInstance(DbModel model, ArrayList<String> dataWhere) {
		Object anObject = null;
		String sFields = "", sSql = "", sWhere = "1=1";
		int i = 0;
		ArrayList<DBField> fields;
		ResultSet rs;
		Field campo;

		// Obtengo los campos de la clase
		fields = model.getFields();

		// agarro todos menos el ultimo por la ','
		for (i = 0; i < fields.size() - 1; i++) {
			// si no quiero cargar la clase no la traigo
			if (fields.get(i).getClassName() != null)
				sFields = sFields.concat(fields.get(i).getDBName() + ", ");
		}
		sFields = sFields.concat(fields.get(i).getDBName());

		// genero el where
		if (!dataWhere.isEmpty()) {
			i = 0;
			// Recorro buscando las pk y armo el where
			for (DBField e : fields) {
				if (e.isPK() && (i < dataWhere.size())) {
					sWhere = sWhere.concat(" AND " + e.getDBName() + "='"
							+ dataWhere.get(i++) + "'");
				}
			}
			// Si no mando la cantidad exacta de paramentros salgo
			if (i != dataWhere.size())
				return null;
		}

		// Genero el sql
		sSql = String.format("SELECT %s FROM %s WHERE %s", sFields, model
				.getTable(), sWhere);

		try {
			// Obtengo la clase
			Class clase = Class.forName(model.getObjClass());

			// Genero una instancia de la clase
			anObject = clase.newInstance();

			// Obtengo los datos de la db
			// this.open();
			if (!this.isOpened()) {
				logger.error("La conexion a la base de datos no esta hecha");
				return null;
			}
			// ejecuto el query
			rs = this.executeQuery(sSql);

			// Si no devuelve ningun dato no devuelvo instancia
			if (rs == null) {
				return null;
			}

			// muevo al primero
			rs.next();

			// Recorro todos los campos que tengo que cargar en la instancia
			for (DBField e : fields) {
				if (e.getClassName() != null) {
					campo = clase.getDeclaredField(e.getClassName());
					campo.setAccessible(true);
					switch (e.getType()) {
					case DB_INT:
						campo.set(anObject, rs.getInt(e.getDBName()));
						break;
					case DB_STRING:
						campo.set(anObject, rs.getString(e.getDBName()));
						break;
					default:
						// todo
						;
					}
				}
			}

		} catch (ClassNotFoundException e) {
			logger.error("ClassNotFoundException:" + e.getMessage());
		} catch (SQLException e) {
			logger.error("SQLException:" + e.getMessage());
		} catch (IllegalAccessException e) {
			logger.error("IllegalAccessException:" + e.getMessage());
		} catch (InstantiationException e) {
			logger.error("InstantiationException:" + e.getMessage());
		} catch (NoSuchFieldException e) {
			logger.error("NoSuchFieldException:" + e.getMessage());
		} finally {
			this.endQuery();
		}
		// anObject = clase.newInstance();

		return anObject;
	}
}
