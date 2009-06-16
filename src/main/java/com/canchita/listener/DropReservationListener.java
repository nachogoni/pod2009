package com.canchita.listener;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.BookingException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.UserException;
import com.canchita.service.BookingService;
import com.canchita.service.BookingServiceProtocol;

public class DropReservationListener extends TimerTask implements
		ServletContextListener {

	static final long interval = 1000 * 5 * 60L;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new DropReservationListener(), interval, interval);
	}

	@Override
	public void run() {

		BookingServiceProtocol bookingService = new BookingService();
		try {
			Collection<Booking> cancelableBookings = bookingService
					.getCancelableBookings();

			for (Booking booking : cancelableBookings) {
				if (bookingService.tryCancel(booking))
					System.out.println("Tirando la reserva con id "
							+ booking.getId());
			}

		} catch (PersistenceException e) {
			e.printStackTrace();
		} catch (UserException e) {
			e.printStackTrace();
		} catch (BookingException e) {
			e.printStackTrace();
		}

	}

}