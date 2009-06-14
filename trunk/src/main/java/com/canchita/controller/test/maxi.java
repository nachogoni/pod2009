package com.canchita.controller.test;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.DAO.db.ComplexDB;
import com.canchita.DAO.db.ExpirationDB;
import com.canchita.model.booking.Expiration;
import com.canchita.model.complex.Complex;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;

/**
 * Servlet implementation class maxi
 */
public class maxi extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public maxi() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Expiration prueba = new Expiration();
		prueba.setId(3L);
		prueba.setBookingLimit(1231);
		prueba.setDepositLimit(112312);
		prueba.setScoreFrom(11231230);
		prueba.setScoreTo(232323232);

		ExpirationDB lala = ExpirationDB.getInstance();
		ComplexDB complexDB = ComplexDB.getInstance();

		System.out.println(lala);
		System.out.println(complexDB);
		Complex complex = null;

		try {
			complex = complexDB.getById(1L);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Field field = new Field(complex, "sarasa");
		field.setId(1L);

		try {
			lala.update(prueba);
		} catch (PersistenceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			Expiration otra = lala.getByScore(complex, 3000);
//			System.out.println(otra.getBookingLimit() + "\n"
//					+ otra.getDepositLimit() + "\n" + otra.getId() + "\n"
//					+ otra.getScoreFrom() + "\n" + otra.getScoreTo());
//		} catch (ElementNotExistsException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		// Collection<Expiration> lista = lala.getAll(complex);
		//		
		// for (Expiration expiration : lista) {
		// System.out.println(expiration.getBookingLimit() + "\n"
		// + expiration.getDepositLimit() + "\n"
		// + expiration.getScoreFrom() + "\n"
		// + expiration.getScoreTo());
		// }
		// try {
		// complex = complexDB.getById(1L);
		// } catch (PersistenceException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//		
		// try {
		// lala.save(complex, prueba);
		// } catch (PersistenceException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
