package com.canchita.DAO.db;

import java.util.Iterator;
import java.util.List;

import org.joda.time.DateTime;

import com.canchita.DAO.BookingDAO;
import com.canchita.DAO.db.builders.CountBuilder;
import com.canchita.DAO.db.builders.ReservationBuilder;
import com.canchita.model.booking.Booking;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;

public class BookingDB extends AllDB implements BookingDAO {

	private static BookingDB instance;

	static {
		instance = new BookingDB();
	}

	private BookingDB() {
	}

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
		String query = "SELECT \"reservation_id\", \"user_id\", \"state\", "
				+ "to_char(start_date,'DD-MON-RRRR HH24:MI:SS') as start_date, "
				+ "to_char(end_date,'DD-MON-RRRR HH24:MI:SS') as end_date "
				+ "FROM RESERVATION WHERE \"reservation_id\" = ?";

		List<Booking> results = executeQuery(query, new Object[] { id },
				ReservationBuilder.getInstance());

		if (results.isEmpty())
			throw new ElementNotExistsException();

		return results.get(0);
	}

	@Override
	public Iterator<Booking> getComplexBookings(Long complexId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Booking> getFieldBookings(Long fieldId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Booking> getFieldBookings(Long fieldId, DateTime date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(Booking booking) throws PersistenceException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean viewAvailability(Booking booking) {
		// TODO Auto-generated method stub
		return false;
	}

}
