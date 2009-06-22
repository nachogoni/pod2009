package com.canchita.DAO.db;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.ReservationBuilder;
import com.canchita.DAO.factory.FactoryMethod;
import com.canchita.model.booking.Booking;
import com.canchita.model.booking.BookingStatus;
import com.canchita.model.booking.Expiration;
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
	public void cancel(Long id) {
		String query = "UPDATE RESERVATION SET \"state\" = ? WHERE \"reservation_id\" = ?";
		executeUpdate(query, new Object[] { BookingStatus.CANCELLED.getIndex(),
				id });

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
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date FROM RESERVATION WHERE \"reservation_id\" = ?";

		List<Booking> results = executeQuery(query, new Object[] { id },
				ReservationBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException(
					"No existe esa reserva en nuestros registros");

		return results.get(0);
	}

	@Override
	public List<Booking> getComplexBookings(Long complexId) {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date"
				+ " FROM RESERVATION WHERE \"state\" <> ? AND \"field_id\" IN "
				+ "(SELECT \"field_id\" FROM FIELD WHERE \"complex_id\" = ?)";

		List<Booking> results = executeQuery(query, new Object[] {
				BookingStatus.CANCELLED.getIndex(), complexId },
				ReservationBuilder.getInstance());

		return results;
	}

	@Override
	public List<Booking> getFieldBookings(Long fieldId) {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date"
				+ " FROM RESERVATION WHERE \"state\" <> ? AND \"field_id\" = ?";

		List<Booking> results = executeQuery(query, new Object[] {
				BookingStatus.CANCELLED.getIndex(), fieldId },
				ReservationBuilder.getInstance());

		return results;
	}

	@Override
	public Iterator<Booking> getFieldBookings(Long fieldId, DateTime date) {

		String sqlDate = "to_date (to_char ( TO_TIMESTAMP_TZ('"
				+ date
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD'), 'YYYY-MON-DD')";

		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"cost\", \"paid\", \"state\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date FROM RESERVATION WHERE "
				+ sqlDate
				+ " IN (to_date (to_char (\"start_date\", 'YYYY-MON-DD'), 'YYYY-MON-DD'),"
				+ "to_date (to_char (\"end_date\", 'YYYY-MON-DD'), 'YYYY-MON-DD'))";

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

		String c = "TO_TIMESTAMP_TZ('" + booking.getExpirationDate()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD')";

		String query = "INSERT INTO RESERVATION VALUES (NULL, ?, ?, ?," + a
				+ ", " + b + ",?,?," + c + ")";

		try {
			executeUpdate(query, new Object[] { booking.getOwner().getId(),
					booking.getItem().getId(), booking.getState().getIndex(),
					booking.getCost(), booking.getPaid() });
		} catch (RuntimeException re) {
			Throwable sql = re.getCause();

			if (sql instanceof SQLException
					&& sql.getMessage().contains("BOOKING_UNIQUE")) {
				throw new ElementExistsException(
						"Ya existe una reserva en ese horario");
			} else {
				throw re;
			}

		}
		String getBooking = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date FROM RESERVATION WHERE \"field_id\" = ?"
				+ " AND \"start_date\" = " + a + " AND \"end_date\" = " + b;

		List<Booking> results = executeQuery(getBooking, new Object[] { booking
				.getItem().getId() }, ReservationBuilder.getInstance());

		return results.get(0);
	}

	@Override
	public Collection<Booking> getDownBookings(Long complexId) {

		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date FROM RESERVATION WHERE WHERE \"state\" = ?"
				+ " AND \"field_id\" IN "
				+ "(SELECT \"field_id\" FROM FIELD WHERE \"complex_id\" = ?)";

		List<Booking> results = executeQuery(query, new Object[] {
				BookingStatus.CANCELLED.getIndex(), complexId },
				ReservationBuilder.getInstance());

		return results;
	}

	@Override
	public Collection<Booking> getDownBookings(String province,
			String locality, String neighbourhood, Long listCount) {

		String query = "SELECT \"reservation_id\", \"user_id\", RESERVATION.\"field_id\""
				+ ", RESERVATION.\"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date"
				+ " FROM RESERVATION, FIELD, COMPLEX WHERE FIELD.\"complex_id\" = COMPLEX.\"complex_id\" AND "
				+ " RESERVATION.\"field_id\" = FIELD.\"field_id\" AND \"start_date\" > SYSDATE AND "
				+ " RESERVATION.\"state\" = ? AND lower(COMPLEX.\"state\") LIKE lower(?) AND lower(COMPLEX.\"city\") LIKE (?) AND "
				+ " lower(\"neighbourhood\") LIKE lower(?) AND rownum <= ? ORDER BY \"start_date\"";

		if (province == null || province.equals("")) {
			province = "%";
		}

		if (locality == null || locality.equals("")) {
			locality = "%";
		}

		if (neighbourhood == null || neighbourhood.equals("")) {
			neighbourhood = "%";
		}

		List<Booking> results = executeQuery(query, new Object[] {
				BookingStatus.CANCELLED.getIndex(), province, locality,
				neighbourhood, listCount }, ReservationBuilder.getInstance());

		return results;
	}

	@Override
	public void update(Booking booking) throws ElementExistsException {
		String a = "TO_TIMESTAMP_TZ('" + booking.getSchedule().getStartTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD')";
		String b = "TO_TIMESTAMP_TZ('" + booking.getSchedule().getEndTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD')";

		DateTime expDate = booking.getExpirationDate();

		String expDateString = (expDate == null) ? "null" : "'" + expDate + "'";

		String c = "TO_TIMESTAMP_TZ(" + expDateString
				+ ", 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD')";

		String query = "UPDATE RESERVATION SET \"user_id\" = ?, \"field_id\" = ?"
				+ ", \"state\" = ?, \"cost\" = ?, \"paid\" = ?, \"start_date\" = "
				+ a
				+ " ,\"end_date\" = "
				+ b
				+ " , \"expiration_date\" = "
				+ c
				+ " WHERE \"reservation_id\" = ?";

		try {
			executeUpdate(query, new Object[] { booking.getOwner().getId(),
					booking.getItem().getId(), booking.getState().getIndex(),
					booking.getCost(), booking.getPaid(), booking.getId() });
		} catch (RuntimeException re) {
			Throwable sql = re.getCause();

			if (sql instanceof SQLException
					&& sql.getMessage().contains("BOOKING_UNIQUE")) {
				throw new ElementExistsException(
						"Ya existe una reserva en ese horario");
			} else {
				throw re;
			}

		}
	}

	@Override
	public List<Booking> getAllBookings() {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date FROM RESERVATION"
				+ " WHERE \"state\" <> ?";

		List<Booking> results = executeQuery(query,
				new Object[] { BookingStatus.CANCELLED.getIndex() },
				ReservationBuilder.getInstance());

		return results;
	}

	@Override
	public List<Booking> getCancelableBookings() {
		String query = "SELECT \"reservation_id\", \"user_id\", \"field_id\""
				+ ", \"state\", \"cost\", \"paid\", to_char(\"start_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as start_date, to_char(\"end_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"')"
				+ " as end_date, to_char(\"expiration_date\",'YYYY-MM-DD\"T\"HH24:MI:SS\"Z\"') as expiration_date FROM RESERVATION"
				+ " WHERE \"state\" = ? OR \"state\" = ?";

		List<Booking> results = executeQuery(query, new Object[] {
				BookingStatus.BOOKED.getIndex(),
				BookingStatus.HALF_PAID.getIndex() }, ReservationBuilder
				.getInstance());

		return results;
	}

	@Override
	public boolean tryCancel(Booking booking, Expiration expiration) {
		String query = "SELECT COUNT(*) AS COUNT FROM RESERVATION WHERE \"reservation_id\" = ?";

		query = query
				+ " AND \"expiration_date\" < SYSDATE+("
				+ (booking.getState() == BookingStatus.BOOKED ? expiration
						.getBookingLimit() : expiration.getDepositLimit())
				+ "/24)";

		List<Integer> results = executeQuery(query, new Object[] { booking
				.getId() }, CountBuilder.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public boolean hasBookings(Long id) {

		String query = "SELECT COUNT(*) AS COUNT FROM RESERVATION WHERE \"field_id\" = ?"
				+ " AND ( \"state\" = ? OR \"state\" = ? )";

		List<Integer> results = executeQuery(query, new Object[] { id,
				BookingStatus.BOOKED.getIndex(),
				BookingStatus.HALF_PAID.getIndex() }, CountBuilder
				.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public boolean complexHasBookings(Long id) {
		String query = "SELECT COUNT(*) AS COUNT FROM RESERVATION,FIELD,COMPLEX"
				+ " WHERE FIELD.\"complex_id\" = COMPLEX.\"complex_id\" AND"
				+ " RESERVATION.\"field_id\" = FIELD.\"field_id\""
				+ " AND COMPLEX.\"complex_id\" = ? AND"
				+ " ( RESERVATION.\"state\" = ? OR RESERVATION.\"state\" = ? )";

		List<Integer> results = executeQuery(query, new Object[] { id,
				BookingStatus.BOOKED.getIndex(),
				BookingStatus.HALF_PAID.getIndex() }, CountBuilder
				.getInstance());

		return results.get(0) > 0;
	}

	@Override
	public boolean viewAvailability(Booking booking) {
		String sqlDateFrom = "to_date (to_char ( TO_TIMESTAMP_TZ('"
				+ booking.getSchedule().getStartTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";

		String sqlDateTo = "to_date (to_char ( TO_TIMESTAMP_TZ('"
				+ booking.getSchedule().getEndTime()
				+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";

		String query = "SELECT COUNT(*) AS COUNT FROM RESERVATION WHERE ( "
			+ sqlDateFrom
			+ " < to_date (to_char (\"end_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS') "
			+ "AND "
			+ sqlDateTo
			+ " > to_date (to_char (\"start_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS') ) ) )";
	
		List<Integer> results = executeQuery(query, new Object[] {},
				CountBuilder.getInstance());

		return results.get(0) == 0;
	}

	@Override
	public boolean viewAvailability(DateTime startTime, DateTime endTime) {
		String sqlDateFrom = "to_date (to_char ( TO_TIMESTAMP_TZ('"
			+ startTime
			+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";
		
		String sqlDateTo = "to_date (to_char ( TO_TIMESTAMP_TZ('"
			+ endTime
			+ "', 'YYYY-MM-DD\"T\"HH24:MI:SS.FFTZD'), 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS')";
		
		String query = "SELECT COUNT(*) AS COUNT FROM RESERVATION WHERE ( "
			+ sqlDateFrom
			+ " < to_date (to_char (\"end_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS') "
			+ "AND "
			+ sqlDateTo
			+ " > to_date (to_char (\"start_date\", 'YYYY-MON-DD HH24.MI.SS'), 'YYYY-MON-DD HH24.MI.SS') ) ";
				
		List<Integer> results = executeQuery(query, new Object[] {},
				CountBuilder.getInstance());
		
		return results.get(0) == 0;
		
	}
}
