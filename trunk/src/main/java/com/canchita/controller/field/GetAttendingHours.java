package com.canchita.controller.field;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class GetAttendingHours
 */
public class GetAttendingHours extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Long id = null;
		DateTime date = null;
		JSONObject json = new JSONObject();
	    
		try {
			id = Long.parseLong(request.getParameter("id"));
		}
		catch( NumberFormatException nfe ) {
			try {
				json.put("success", false);
			} catch (JSONException e) {
				this.sendJSON(json,response);
			}
						
		}

		String dateParameter = request.getParameter("date");
		
		DateTimeFormatter parser =
		    DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			date = parser.parseDateTime(dateParameter);
		}catch(IllegalArgumentException iae) {
			try {
				json.put("success", false);
			} catch (JSONException e) {
				this.sendJSON(json,response);
			}
				
		}
		
		//Get Attending Hours and put them on the json object
		
		try {
			json.put("success", true);
		} catch (JSONException e) {	
		}
		finally{
			this.sendJSON(json,response);
		}
		
	}

	private void sendJSON(JSONObject json, HttpServletResponse response) throws IOException {
		
		PrintWriter out = response.getWriter();
		
		out.println(json);
		out.flush();
		
	}
	
}