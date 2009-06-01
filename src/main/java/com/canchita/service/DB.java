package com.canchita.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;

import java.sql.*;


/**
 * Servlet implementation class DB
 */
public class DB extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DB() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request,
	 *  HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection conexion = null;
		    
	    response.setContentType("text/html");
	    PrintWriter out = response.getWriter();

	    try {
			//Leemos el driver de Oracle
			Class.forName("oracle.jdbc.driver.OracleDriver");

			//Nos conectamos a la BD local
			conexion = DriverManager.getConnection (
					"jdbc:oracle:thin:@localhost:1521:itba",
					"dgomezvi","tppod");


	        //Decimos que nos hemos conectado 
	    	out.println("<html>");
	    	out.println("<body>");
	    	out.println("<h1>¡Hemos conectado!</h1>");
	    	out.println("</body>");
			out.println("</html>");

		} 
		catch (ClassNotFoundException e1) {
			//Error si no puedo leer el driver de Oracle 
			out.println("ERROR:No encuentro el driver de la BD: "+
						e1.getMessage());
		}
		catch (SQLException e2) {
			//Error SQL: login/passwd mal
			out.println("ERROR:Fallo en SQL: "+e2.getMessage());
		}
		finally {
			//Finalmente desconecto de la BD
			try {
				if (conexion!=null)
					conexion.close();
			} catch (SQLException e3) {
				out.println("ERROR:Fallo al desconectar de la BD: "+
					e3.getMessage());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
