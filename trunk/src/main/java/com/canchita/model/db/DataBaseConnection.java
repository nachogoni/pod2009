package com.canchita.model.db;

import java.awt.image.RescaleOp;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.canchita.controller.complex.AddComplex;

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

		//Si la conexion no fue abierta
		if (this.conexion == null)
		{
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
	
	public ResultSet executeQuery(String sSql){
		ResultSet ret = null;
		
		if (this.conexion != null)
		{
			try{
				stmt = conexion.createStatement();
				
				ret = stmt.executeQuery(sSql);
				
			}catch(Exception e){
				logger.error("Error al ejecutar la sentencia: " + sSql);
			}
		}
		
		return ret;
	}
	
	public void endQuery()
	{
		try{
			stmt.close();					
		}catch (SQLException e) {
			logger.error("Error al finalizar el statement");
		}
	}

	/**
	 * Ejecuta una sentencia SQL
	 * 
	 * @param sSql
	 * @return 
	 */
	public boolean execute(String sSql){
		boolean ret;
		
		try{
			/* creo el statement*/
			stmt = conexion.createStatement();
			
			ret = stmt.execute(sSql);
						
			stmt.close();			
		}catch(Exception e){
			logger.error("Error al ejecutar la sentencia: " + sSql);
			ret = false;
		}
		
		return ret;
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
	
	public Object loadInstance(TestDBModel model){
		Object anObject = null;
		String sFields = "", sSql = "";
		int i=0;
		ArrayList<DBField> fields;
		ResultSet rs;
		Object parametrosSet[];
		Class[] parametrosMethod; 
		Method cMethod;
		Field campos[], campo;
		
		// armo el sql
		fields = model.getFields();
		
		for(i=0; i<fields.size()-1;i++){
			sFields = sFields.concat(fields.get(i).getDBName() + ", ");
		}
		sFields = sFields.concat(fields.get(i).getDBName());
		
		sSql = String.format("SELECT %s FROM %s", sFields, model.getTable());
		
		System.out.println(sSql);
		
		System.out.println("open" + this.open());
		
			
		//clase = Class.forName();
		try{
			//Obtengo la clase
			Class clase = Class.forName("com.canchita.model.db." + model.getObjClass());

			//Genero una instancia de la clase
			anObject = clase.newInstance();
			
			//Creo los parametros del metodo (String)
			parametrosMethod = new Class[1];
			parametrosMethod[0] = Class.forName("java.lang.String");
			
			//Obtengo el metodo
			cMethod = clase.getMethod("setName", parametrosMethod);
			
			//Creo los parametros del metodo
			
			//Obtengo los datos de la base de datos
			rs = this.executeQuery(sSql);
			rs.next();	
			System.out.println(rs.toString());
			
			parametrosSet = new Object[1];
			//parametrosSet[0] = rs.getString("nombre");
			parametrosSet[0] = "sarasa";
			
			//termino el query
			this.endQuery();
			
			//Invoco al metodo
			cMethod.invoke(anObject, parametrosSet);
			
			campo = clase.getDeclaredField("name");
			campo.setAccessible(true);
			campo.set(anObject, "jeropa");
			
			campo = clase.getDeclaredField("hola");
			campo.setAccessible(true);
			campo.set(anObject, "JOIJ");
			
		}catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException:" + e.getMessage());
		}catch (SQLException e) {
			System.out.println("SQLException:" + e.getMessage());
		}catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException:" + e.getMessage());
		}catch (InstantiationException e) {
			System.out.println("InstantiationException:" + e.getMessage());
		}catch (NoSuchMethodException e) {
			System.out.println("NoSuchMethodException:" + e.getMessage());
		}catch (InvocationTargetException e) {
			System.out.println("NoSuchMethodException:" + e.getMessage());
		}catch (NoSuchFieldException e) {
			System.out.println("NoSuchFieldException:" + e.getMessage());
		}
		//anObject = clase.newInstance();
					
		
		
		System.out.println("close");
		
		
		return anObject;
	}
}
