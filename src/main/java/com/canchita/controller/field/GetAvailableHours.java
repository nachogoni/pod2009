package com.canchita.controller.field;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.canchita.model.booking.Schedule;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;

/**
 * Servlet implementation class GetAttendingHours
 */
public class GetAvailableHours extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Long id = null;
		DateTime date = null;
		JSONObject json = new JSONObject();

		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			try {
				json.put("success", false);
			} catch (JSONException e) {
				this.sendJSON(json, response);
			}

		}

		String dateParameter = request.getParameter("date");

		DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			date = parser.parseDateTime(dateParameter);
		} catch (IllegalArgumentException iae) {
			try {
				json.put("success", false);
			} catch (JSONException e) {
				this.sendJSON(json, response);
			}

		}

		// Get Attending Hours and put them on the json object

		FieldServiceProtocol fieldService = new FieldService();

		Iterator<Schedule> schedules = fieldService.getAvailableHours(id, date);
		JSONArray values = new JSONArray();
		JSONArray names = new JSONArray();
		int i = 0;

		while (schedules.hasNext()) {
			Schedule schedule = (Schedule) schedules.next();

			String representation = schedule.getStartTime().toString("HH:mm")
					+ " - " + schedule.getEndTime().toString("HH:mm");

			System.out.println(representation);
			
			values.put(representation);
			names.put(i++);
		}

		try {
			json.put("availability", values.toJSONObject(names));
			json.put("success", true);
		} catch (JSONException e) {
		} finally {
			this.sendJSON(json, response);
		}

	}

	private void sendJSON(JSONObject json, HttpServletResponse response)
			throws IOException {

		PrintWriter out = response.getWriter();

		out.println(json);
		out.flush();

	}

}