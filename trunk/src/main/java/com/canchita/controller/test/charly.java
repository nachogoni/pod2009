package com.canchita.controller.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;

import com.canchita.DAO.db.BookingDB;
import com.canchita.DAO.db.FieldDB;
import com.canchita.DAO.db.UserDB;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;
import com.canchita.model.user.CommonUser;

/**
 * Servlet implementation class charly
 */
public class charly extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public charly() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("Entré a Charly!");

		Field field = null;
		try {
			field = FieldDB.getInstance().getById(1L);
		} catch (ElementNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		CommonUser user = null;
		try {
			user = UserDB.getInstance().getCommonUserById(1L);
		} catch (ElementNotExistsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DateTime start = new DateTime();
		DateTime end = new DateTime().plusHours(2);
		Schedule sched = new Schedule(start, end);

		Booking lala = new Booking(1L, field, user, 0, sched);

		try {
			BookingDB.getInstance().save(lala);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
