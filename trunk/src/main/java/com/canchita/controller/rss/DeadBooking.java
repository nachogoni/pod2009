package com.canchita.controller.rss;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.model.booking.Booking;
import com.canchita.model.rss.RSS;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.feed.synd.SyndImage;
import com.sun.syndication.feed.synd.SyndImageImpl;
import com.sun.syndication.io.SyndFeedOutput;

/**
 * Servlet implementation class DeadBooking
 */
public class DeadBooking extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeadBooking() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		Booking booking = null;
		String neighbourhood = null;
		
		// Generar el feed para el rss
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType(new String("rss_2.0"));
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
		SyndEntry entry;
		SyndContent description;

		String scheme = request.getScheme(); // http
		String serverName = request.getServerName(); // hostname.com
		int serverPort = request.getServerPort(); // 80
		String contextPath = request.getContextPath(); // /mywebapp

		String url = scheme + "://" + serverName + ":" + serverPort + contextPath;


		neighbourhood = request.getParameter("neighbourhood");
		
		String baseURL = url;

		Collection<Booking> bookings = null;
		
		try {
			bookings = RSS.generateDownBookings(neighbourhood);
		} catch (Exception e) {
			logger.error("RSS Feed - DeadBooking error at " + (new Date()).toString() + e.getMessage());
			e.printStackTrace();
			return;
		}

		try {
			// Informacion del RSS
			feed.setTitle("Reservas caidas - RSS Feed");
			feed.setLink(baseURL);
			feed.setCopyright("Copyright 2009 Canchita - All rights reserved");
			feed.setLanguage("es-ar");
			SyndImage image = new SyndImageImpl();
			image.setUrl(baseURL + "/img/fieldPortada.jpg");
			image.setDescription("Canchita");
			image.setLink(baseURL);
			image.setTitle("Canchita");
			feed.setImage(image);
			feed.setDescription("RSS Feed con las reservas caidas, aprovechalas, ahora solo necesitas llamar a tus amigos! canchita.com!");
			feed.setPublishedDate(new Date());

			for (Iterator<Booking> i = bookings.iterator(); i.hasNext();) {
				booking = i.next();

				entry = new SyndEntryImpl();
				entry.setTitle(booking.getItem().getName());
				entry.setLink(new String(baseURL + "/field/detailedview?id="
						+ booking.getItem().getId() + "&viewDetails=Detalles"));
				entry.setPublishedDate(new Date());
				description = new SyndContentImpl();
				description.setType("text/html");
				description.setValue(new String("Se cayo la reserva para la cancha <i>" + booking.getItem().getName()
						+ " (" + booking.getItem().getDescription() + ")"
						+ "</i>, en el complejo <a href=\"" + baseURL
						+ "/DetailedViewComplex?id="
						+ booking.getItem().getComplex().getId()
						+ "&viewDetails=Detalles\">"
						+ booking.getItem().getComplex().getName() + "</a>"
						+ "</i> ubicado en "
						+ booking.getItem().getComplex().getPlace().toString()
						+ ". La reserva era para " + 
						((booking.getSchedule().getStartTime().toLocalDate().toString().equals(new Date()))?"hoy, ":"el dia ") 
						+ booking.getSchedule().getStartTime().toLocalDate().toString()
						+ " a las " + booking.getSchedule().getStartTime().toString("HH:MM") + " hasta las "
						+ booking.getSchedule().getEndTime().toString("HH:MM") + " del dia "
						+ booking.getSchedule().getEndTime().toLocalDate()));
				
				entry.setDescription(description);
				entries.add(entry);
			}

			// Cargo las entradas en el feed
			feed.setEntries(entries);

			// Devuelvo el rss
			Writer writer = response.getWriter();
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, writer);

			logger.info("RSS Feed - DeadBooking created at " + (new Date()).toString());

		} catch (Exception ex) {
			logger.error("RSS Feed - DeadBooking error at " + (new Date()).toString() + ex.toString());
			ex.printStackTrace();
		}

	}

}
