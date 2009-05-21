package com.canchita.controller.helper;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;

/**
 * 
 * @author Pablo Federico Abramowicz
 * @author Martín Esteban Cabral
 * @author Darío Maximiliano Gomez Vidal
 * @author Juan Ignacio Goñi
 * @author Martín Palombo
 * @author Carlos Manuel Sessa
 * 
 */

public class UrlMapper {

	private static final UrlMapper mapper = new UrlMapper();
	private static final String DEFAULT = "DEFAULT";
	private static final String ROOT_DIR = "/WEB-INF/views/";
	private Map<String, String> success;
	private Map<String, String> failure;

	private UrlMapper() {

		initializeSuccess();
		initializeFailure();
	}

	/**
	 * Get the UrlMapper instance
	 * 
	 * @return an urlMapper instance
	 */
	public static UrlMapper getInstance() {
		return mapper;
	}

	/**
	 * Initializes the success map
	 */
	private void initializeSuccess() {
		this.success = new HashMap<String, String>();

		success.put("ListComplex", ROOT_DIR + "ListComplex.jsp");

	}

	/**
	 * Initializes the failure map
	 */
	private void initializeFailure() {
		this.failure = new HashMap<String, String>();

	}

	/**
	 * Finds success server path.
	 * 
	 * @param servlet
	 *            who needs to find its success url.
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	public String onSuccess(HttpServlet servlet) {

		return this.findUrl(this.success, servlet);

	}

	/**
	 * Finds failure server path.
	 * 
	 * @param servlet
	 *            who needs to find its failure url.
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	public String onFailure(HttpServlet servlet) {

		return this.findUrl(this.failure, servlet);

	}

	/**
	 * Finds the path of the given servlet.
	 * 
	 * @param map
	 *            where the path is mapped.
	 * @param servlet
	 *            who needs to find its failure url.
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	private String findUrl(Map<String, String> map, HttpServlet servlet) {

		String name = this.getName(servlet);
		String url;

		if (map.containsKey(name)) {
			url = map.get(name);
		} else {
			url = map.get(UrlMapper.DEFAULT);
		}

		return url;
	}

	/**
	 * Gets the unqualified name of the given servlet.
	 * 
	 * @param servlet
	 * @return unqualified servlet name.
	 */
	private String getName(HttpServlet servlet) {

		Class<? extends HttpServlet> cls = servlet.getClass();
		String name = cls.getName();

		if (name.lastIndexOf('.') > 0) {
			name = name.substring(name.lastIndexOf('.') + 1);
		}

		return name;

	}

}
