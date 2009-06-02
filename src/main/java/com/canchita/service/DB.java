package com.canchita.service;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.canchita.model.db.DataBaseConnection;
import com.canchita.model.db.TestDBClass;
import com.canchita.model.db.TestDBModel;
import com.canchita.controller.GenericServlet;
import java.sql.*;
import java.util.ArrayList;


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
	    Statement stmt = null;
	    DataBaseConnection db;
	    TestDBClass testclass;

	    db = new DataBaseConnection();
		if (db.open()){
				
			
	       //Decimos que nos hemos conectado 
	    out.println("<html>");
	    out.println("<body>");
	    out.println("<h1>¡Hemos conectado!</h1>");
	    
	    try{
	    	ArrayList<String> aPk = new ArrayList<String>();
	    	aPk.add("1");
	    	testclass = (TestDBClass)db.loadInstance(new TestDBModel(), aPk);
	    	System.out.println(testclass.getName());
	    	System.out.println(testclass.getHola());
	    	System.out.println(testclass.getKaka());
	    	
	    	ResultSet rcursor = db.executeQuery("SELECT * FROM \"COMPLEX\"");
	    	
	    	while(rcursor.next()){
	    		out.println("Nombre: " + rcursor.getString("name") + "<br/>");
	    	}
	    	
	    	db.endQuery();
	    }catch (SQLException e1) {
			// TODO: handle exception
	    	System.out.println(e1.getMessage());
	    	out.println("ERROR: " + e1.getMessage());
		}catch (Exception e) {
			// TODO: handle exception
	    	out.println("Error!");
	    	System.out.println(e.getMessage());
		}
	    
	    out.println("</body>");
		out.println("</html>");
		}

		db.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
