package com.canchita.DAO.db;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.ReservationBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.model.booking.Booking;
import com.canchita.model.exception.ElementExistsException;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public class BookingDB extends AllDB implements BookingDAO {

	private static BookingDB instance;

	static {
		instance = new BookingDB();
	}

	private BookingDB() {
	}

	@FactoryMethod
	public static BookingDB getInstance() {
		return instance;
	}

	@Override
	public void delete(Long id) {
		String query = "DELETE FROM RESERVATION WHERE \"reservation_id\" = ?";
		executeUpdate(query, new Object[] { id });

	}

	@Override
	public boolean exists(Booking booking) {
		String query = "SELECT COUNT(*) AS COUNT FROM RESERVATION WHERE \"reservation_id\" = ?";

		List<Integer> results = executeQuery(query, new Object[] { booking
				.getId() }, CountBuilder.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public Booking getById(Long id) throws ElementNotExistsException {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"cost\", \"paid\", \"state\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date FROM RESERVATION WHERE \"reservation_id\" = ?";

		List<Booking> results = executeQuery(query, new Object[] { id },
				ReservationBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException(
					"No existe esa reserva en nuestros registros");

		return results.get(0);
	}

	@Override
	public Iterator<Booking> getComplexBookings(Long complexId) {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date FROM RESERVATION WHERE \"field_id\" IN "
				+ "(SELECT \"field_id\" FROM FIELD WHERE \"complex_id\" = ?)";

		List<Booking> results = executeQuery(query, new Object[] { complexId },
				ReservationBuilder.getInstance());

		return results.iterator();
	}

	@Override
	public Iterator<Booking> getFieldBookings(Long fieldId) {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date FROM RESERVATION WHERE \"field_id\" = ?";

		List<Booking> results = executeQuery(query, new Object[] { fieldId },
				ReservationBuilder.getInstance());

		return results.iterator();
	}

	@Override
	public Iterator<Booking> getFieldBookings(Long fieldId, DateTime date) {

		String sqlDate = "to_date (to_char ( TO_TIMESTAMP_TZ('"
				+ date
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD'), 'YYYY-MON-DD')";

		String query = "SELECT * FROM RESERVATION WHERE "
				+ sqlDate
				+ " IN (to_date (to_char (\"start_date\", 'YYYY-MON-DD'), 'YYYY-MON-DD'),"
				+ "to_date (to_char (\"end_date\", 'YYYY-MON-DD'), 'YYYY-MON-DD'))";

		System.out.println();

		List<Booking> results = executeQuery(query, new Object[] {},
				ReservationBuilder.getInstance());

		return results.iterator();
	}

	@Override
	public Booking save(Booking booking) throws PersistenceException {
		String a = "TO_TIMESTAMP_TZ('" + booking.getSchedule().getStartTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD')";
		String b = "TO_TIMESTAMP_TZ('" + booking.getSchedule().getEndTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD')";
		String query = "INSERT INTO RESERVATION VALUES (NULL, ?, ?, ?," + a
				+ ", " + b + ",?,?)";

		try {
			executeUpdate(query, new Object[] { booking.getOwner().getId(),
					booking.getItem().getId(), booking.getState().ordinal(),
					booking.getCost(), booking.getPaid() });
		} catch (RuntimeException re) {
			Throwable sql = re.getCause();

			if (sql instanceof SQLException) {
				if (sql.getMessage().contains("BOOKING_UNIQUE")) {
					throw new ElementExistsException(
							"Ya existe una reserva en ese horario");
				}
			}

		}
		String getBooking = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date FROM RESERVATION WHERE \"field_id\" = ?"
				+ " AND \"start_date\" = " + a + " AND \"end_date\" = " + b;

		List<Booking> results = executeQuery(getBooking, new Object[] { booking
				.getItem().getId() }, ReservationBuilder.getInstance());

		return results.get(0);
	}

	@Override
	public boolean viewAvailability(Booking booking) {
		String sqlDateFrom = "to_date (to_char ( TO_TIMESTAMP_TZ('"
				+ booking.getSchedule().getStartTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";

		String sqlDateTo = "to_date (to_char ( TO_TIMESTAMP_TZ('"
				+ booking.getSchedule().getEndTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";

		String query = "SELECT COUNT(*) FROM RESERVATION WHERE ("
				+ sqlDateFrom
				+ " >= to_date (to_char (\"start_date\", 'YYYY-MON-DD HH24.MI.SS') 'YYYY-MON-DD HH24.MI.SS') "
				+ " AND "
				+ sqlDateFrom
				+ " <= to_date (to_char (\"end_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS'))"
				+ "OR ("
				+ sqlDateTo
				+ " >= to_date (to_char (\"start_date\", 'YYYY-MON-DD HH24.MI.SS') 'YYYY-MON-DD HH24.MI.SS') "
				+ " AND "
				+ sqlDateTo
				+ " <= to_date (to_char (\"end_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS'))";

		List<Integer> results = executeQuery(query, new Object[] { booking
				.getId() }, CountBuilder.getInstance());

		return results.get(0) == 0;

	}

}
