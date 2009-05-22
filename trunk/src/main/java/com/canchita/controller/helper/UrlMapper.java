package com.canchita.controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.complex.ListComplex;

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

/*
 * TODO falta agregar las paginas default de success y failure no se me ocurre
 * una buena success default lo mandaria al home igual como que siempre va a
 * ahber una success.
 * 
 * La default de error que sea una generica tipo pasaron cosas locas so sorry
 */

public class UrlMapper {

	private static final UrlMapper mapper = new UrlMapper();
	private static final String DEFAULT = "DEFAULT";
	private static final String ROOT_DIR = "/WEB-INF/views/";
	private Map<String, String> success;
	private Map<String, String> failure;
	public static final String POST = "POST";
	public static final String GET = "GET";

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

		success.put("ListComplexGET", ROOT_DIR + "ListComplex.jsp");
		success.put("DeleteComplexPOST", ROOT_DIR + "ListComplex.jsp");
		success.put("AddComplexGET", ROOT_DIR + "AddComplexForm.jsp");

	}

	/**
	 * Initializes the failure map
	 */
	private void initializeFailure() {
		this.failure = new HashMap<String, String>();

		failure.put("AddComplexPOST", ROOT_DIR + "AddComplexForm.jsp");

	}

	/**
	 * Finds success server path.
	 * 
	 * @param servlet
	 *            who needs to find its success url.
	 * @param type
	 *            HTTP request type
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	public String onSuccess(HttpServlet servlet, UrlMapperType type) {

		return this.findUrl(this.success, servlet, type);

	}

	/**
	 * Finds failure server path.
	 * 
	 * @param servlet
	 *            who needs to find its failure url.
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	public String onFailure(HttpServlet servlet, UrlMapperType type) {

		return this.findUrl(this.failure, servlet, type);

	}

	/**
	 * Finds the path of the given servlet.
	 * 
	 * @param map
	 *            where the path is mapped.
	 * @param servlet
	 *            who needs to find its failure url.
	 * @param type
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	private String findUrl(Map<String, String> map, HttpServlet servlet,
			UrlMapperType type) {

		String name = this.getName(servlet) + type;
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

	public void forwardSuccess(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type)
			throws ServletException, IOException {

		this.forward(servlet, request, response, type, success);

	}

	public void forwardFailure(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type)
			throws ServletException, IOException {

		this.forward(servlet, request, response, type, failure);

	}

	private void forward(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type,
			Map<String, String> map) throws ServletException, IOException {

		String url = this.findUrl(map, servlet, type);

		request.getRequestDispatcher(url).forward(
				request, response);

	}

}
