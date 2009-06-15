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
import com.canchita.model.field.Field;
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
 * Servlet implementation class LastFields
 */
public class LastFields extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LastFields() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");

		Field field = null;

		String neighbourhood = "a";
		
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
		//String servletPath = request.getServletPath(); // /servlet/MyServlet
		//String pathInfo = request.getPathInfo(); // /a/b;c=123
		//String queryString = request.getQueryString(); // d=789

		// Reconstruct original requesting URL
		//String url = scheme + "://" + serverName + ":" + serverPort + contextPath;// + servletPath;
		String url = scheme + "://" + serverName + ":" + serverPort + contextPath;

        /*if (pathInfo != null) {
            url += pathInfo;
        }
        if (queryString != null) {
            url += "?"+queryString;
        }*/

		String baseURL = url;

		Collection<Field> fields = null;
		
		try {
			fields = RSS.generateNewFields(neighbourhood);
		} catch (Exception e) {
			logger.error("RSS Feed - LastFieds error at " + (new Date()).toString() + e.getMessage());
			e.printStackTrace();
			return;
		}

		try {
			// Informacion del RSS
			feed.setTitle("Ultimas canchas ingresadas - RSS Feed");
			feed.setLink(baseURL);
			feed.setCopyright("Copyright 2009 Canchita - All rights reserved");
			feed.setLanguage("es-ar");
			SyndImage image = new SyndImageImpl();
			image.setUrl(baseURL + "/img/fieldPortada.jpg");
			image.setDescription("Canchita");
			image.setLink(baseURL);
			image.setTitle("Canchita");
			feed.setImage(image);
			feed.setDescription("RSS Feed con las ultimas canchas ingresadas en canchita.com!");
			feed.setPublishedDate(new Date());

			for (Iterator<Field> i = fields.iterator(); i.hasNext();) {
				field = i.next();

				entry = new SyndEntryImpl();
				entry.setTitle(field.getName());
				entry.setLink(new String(baseURL + "/field/detailedview?id="
						+ field.getId() + "&viewDetails=Detalles"));
				entry.setPublishedDate(new Date());
				description = new SyndContentImpl();
				description.setType("text/html");
				description.setValue((new String("<i>" + field.getDescription()
						+ "</i>, en el complejo <a href=\"" + baseURL
						+ "/DetailedViewComplex?id="
						+ field.getComplex().getId()
						+ "&viewDetails=Detalles\">"
						+ field.getComplex().getName() + "</a>"
						+ "</i> ubicado en "
						+ field.getComplex().getPlace().toString())));
				entry.setDescription(description);
				entries.add(entry);
			}

			// Cargo las entradas en el feed
			feed.setEntries(entries);

			// Devuelvo el rss
			Writer writer = response.getWriter();
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, writer);

			logger.info("RSS Feed - LastFieds created at " + (new Date()).toString());

		} catch (Exception ex) {
			logger.error("RSS Feed - LastFieds error at " + (new Date()).toString() + ex.toString());
			ex.printStackTrace();
		}

	}
}
