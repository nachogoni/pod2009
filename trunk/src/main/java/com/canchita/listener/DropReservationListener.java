package com.canchita.listener;

import java.util.Collection;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.canchita.model.booking.Booking;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.service.BookingService;

public class DropReservationListener extends TimerTask implements
		ServletContextListener {

	static final long interval = 1000 * 5L;

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new DropReservationListener(), 0, interval);
	}

	@Override
	public void run() {
//		BookingService bookingService = new BookingService();
//		Collection<Booking> downBookings = null;
//
//		try {
//			downBookings = bookingService.getDownBookings();
//			for (Booking booking : downBookings) {
//				//bookingService.cancel(booking);
//			}
//		} catch (ValidationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (PersistenceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

}