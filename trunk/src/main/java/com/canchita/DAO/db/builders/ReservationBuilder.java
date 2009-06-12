package com.canchita.DAO.db.builders;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.db.FieldDB;
import com.canchita.DAO.db.QueryProcessor;
import com.canchita.DAO.db.UserDB;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.Schedule;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.Field;
import com.canchita.model.user.CommonUser;

public class ReservationBuilder implements QueryProcessor<Booking> {

	private static ReservationBuilder instance;

	static {
		instance = new ReservationBuilder();
	}

	private ReservationBuilder() {
	}

	public static ReservationBuilder getInstance() {
		return instance;
	}

	@Override
	public List<Booking> buildCollection(ResultSet resultSet)
			throws SQLException {

		List<Booking> results = new ArrayList<Booking>();
		Booking aBooking;

		while (resultSet.next()) {

			//Levanto el usuario.
			long userID = resultSet.getLong("user_id");
			CommonUser user = null;
			try {
				user = UserDB.getInstance().getCommonUserById(userID);
			} catch (PersistenceException e) {
				e.printStackTrace();
			}
			
			//Levanto el bookeable (field)
			Long fieldID = resultSet.getLong("field_id");
			Field field = null;
			try {
				field = FieldDB.getInstance().getById(fieldID);
			} catch (ElementNotExistsException e) {
				e.printStackTrace();
			}
			
			//Armo el schedule
			DateTime startDate = new DateTime(resultSet.getString("start_date"));
			DateTime endDate = new DateTime(resultSet.getString("end_date"));
			Schedule schedule = new Schedule(startDate, endDate);

			//Construyo el booking
			aBooking = new Booking( resultSet.getLong("reservation_id"),
					field, user, resultSet.getLong("state"), schedule);
			 
			results.add(aBooking);
		}

		return results;
	}
}
