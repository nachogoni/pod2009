package com.canchita.controller.field;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.canchita.controller.GenericServlet;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;

/**
 * Servlet implementation class GetAttendingHours
 */
public class GetAvailableHours extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");
		
		Long id = null;
		DateTime date = null;
		JSONObject json = new JSONObject();

		try {
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			logger.error("Error leyendo id");
			try {
				json.put("success", false);
				json.put("error", "El identificador de la cancha debe ser numérico");
				this.sendJSON(json, response);
				return;
			} catch (JSONException e) {
				logger.error("Error leyendo id");
				this.sendJSON(json, response);
				return;
			}

		}

		String dateParameter = request.getParameter("date");

		DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");

		try {
			date = parser.parseDateTime(dateParameter);
		} catch (IllegalArgumentException iae) {
			logger.error("Error parseando fecha");
			try {
				json.put("success", false);
				json.put("error", "La fecha debe ser en formato dd/mm/yyyy");
				this.sendJSON(json, response);
				return;
			} catch (JSONException e) {
				logger.error("Error parseando fecha (json)");
				this.sendJSON(json, response);
				return;
			}

		}

		// Get Attending Hours and put them on the json object

		FieldServiceProtocol fieldService = new FieldService();

		Iterator<Schedule> schedules = null;
		try {
			schedules = fieldService.getAvailableHours(id, date);
		} catch (PersistenceException e1) {
			try {
				json.put("success", false);
				json.put("error", e1.getMessage());
				this.sendJSON(json, response);
				return;
			} catch (JSONException e) {
				this.sendJSON(json, response);
				return;
			}

		}
		
		JSONArray values = new JSONArray();
		JSONArray names = new JSONArray();
		int i = 0;

		while (schedules.hasNext()) {
			Schedule schedule = (Schedule) schedules.next();

			String representation = schedule.getStartTime().toString("HH:mm")
					+ " - " + schedule.getEndTime().toString("HH:mm");

			values.put(representation);
			names.put(i++);
		}

		try {
			json.put("availability", values.toJSONObject(names));
			json.put("success", true);
		} catch (JSONException e) {
			logger.error("Excepción seteando disponibilidad");
		} finally {
			this.sendJSON(json, response);
		}

	}

	private void sendJSON(JSONObject json, HttpServletResponse response)
			throws IOException {

		PrintWriter out = response.getWriter();

		out.println(json);
		out.flush();
		out.close();

	}

}