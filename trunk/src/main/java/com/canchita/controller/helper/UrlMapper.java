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
	private static final String FORWARD_ROOT_DIR = "/WEB-INF/views/";
	
	private Map<String, String> successForward;
	private Map<String, String> failureForward;
	private Map<String, String> successRedirect;
	private Map<String, String> failureRedirect;

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
		this.successForward = new HashMap<String, String>();

		
		successForward.put("ListComplexGET", FORWARD_ROOT_DIR + "complex/ListComplex.jsp");
		successForward.put("DeleteComplexPOST", FORWARD_ROOT_DIR + "complex/ListComplex.jsp");
		successForward.put("AddComplexGET", FORWARD_ROOT_DIR + "complex/AddComplexForm.jsp");
		successForward.put("AddComplexPOST", FORWARD_ROOT_DIR + "complex/ListComplex.jsp");
		successForward.put("AddBookingGET", FORWARD_ROOT_DIR + "field/AddBooking.jsp");
		successForward.put("AdminHomeGET", FORWARD_ROOT_DIR + "admin/AdminHome.jsp");
		successForward.put("DetailedViewComplexGET", FORWARD_ROOT_DIR + "complex/ViewComplex.jsp");

		this.successRedirect = new HashMap<String, String>();
		
		this.successRedirect.put("AddComplexPOST","/tp-pod/ListComplex");
		
	}

	/**
	 * Initializes the failure map
	 */
	private void initializeFailure() {
		this.failureForward = new HashMap<String, String>();

		failureForward.put("ListComplexGET", FORWARD_ROOT_DIR + "complex/ListComplex.jsp");
		failureForward.put("AddComplexPOST", FORWARD_ROOT_DIR + "complex/AddComplexForm.jsp");
		failureForward.put("AddBookingGET", FORWARD_ROOT_DIR + "field/AddBooking.jsp");

		this.failureRedirect = new HashMap<String, String>();

	}

	/**
	 * Finds successForward server path.
	 * 
	 * @param servlet
	 *            who needs to find its success url.
	 * @param type
	 *            HTTP request type
	 * @return path to send to @see{RequestDispatcher.getRequestDispatcher}. If
	 *         the request is not mapped it will return the DEFAULT path.
	 */
	public String onSuccess(HttpServlet servlet, UrlMapperType type) {

		return this.findUrl(this.successForward, servlet, type);

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

		return this.findUrl(this.failureForward, servlet, type);

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

		this.forward(servlet, request, response, type, successForward);

	}

	public void forwardFailure(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type)
			throws ServletException, IOException {

		this.forward(servlet, request, response, type, failureForward);

	}

	public void redirectSuccess(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type)
			throws IOException {

		this.redirect(servlet, request, response, type, successRedirect);

	}


	public void redirectFailure(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type)
			throws IOException {

		this.redirect(servlet, request, response, type, failureRedirect);

	}


	
	private void forward(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type,
			Map<String, String> map) throws ServletException, IOException {

		String url = this.findUrl(map, servlet, type);

		request.getRequestDispatcher(url).forward(
				request, response);

	}

	private void redirect(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type,
			Map<String, String> map) throws IOException {

		String url = this.findUrl(map, servlet, type);

		response.sendRedirect(url);

		
	}
}
