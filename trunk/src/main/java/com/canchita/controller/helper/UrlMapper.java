package com.canchita.controller.helper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

		// User
		successForward.put("LoginGET", FORWARD_ROOT_DIR
				+ "user/guest/Login.jsp");
		successForward.put("HomeGET", FORWARD_ROOT_DIR
				+ "user/registered/Home.jsp");
		successForward.put("RegisterGET", FORWARD_ROOT_DIR
				+ "user/guest/Register.jsp");
		successForward.put("ProfileGET", FORWARD_ROOT_DIR
				+ "user/registered/Profile.jsp");

		// ScoreSystem
		successForward.put("ModifyScoreSystemGET", FORWARD_ROOT_DIR
				+ "scoresystem/ModifyScoreSystem.jsp");
		successForward.put("ShowScoreSystemGET", FORWARD_ROOT_DIR
				+ "scoresystem/ShowScoreSystem.jsp");

		// ExpirationPolicy
		successForward.put("ListExpirationPolicyGET", FORWARD_ROOT_DIR
				+ "complex/ListExpirationPolicy.jsp");
		successForward.put("AddExpirationPolicyGET", FORWARD_ROOT_DIR
				+ "complex/AddExpirationPolicy.jsp");
		successForward.put("ModifyExpirationPolicyGET", FORWARD_ROOT_DIR
				+ "complex/ModifyExpirationPolicy.jsp");
		successForward.put("ModifyExpirationPolicyPOST", FORWARD_ROOT_DIR
				+ "complex/ListExpirationPolicy.jsp");

		/* Field */
		successForward.put("ListFieldGET", FORWARD_ROOT_DIR
				+ "field/ListField.jsp");
		successForward.put("AddFieldGET", FORWARD_ROOT_DIR
				+ "field/AddFieldForm.jsp");
		successForward.put("AddFieldPOST", FORWARD_ROOT_DIR
				+ "field/ListField.jsp");
		successForward.put("ModifyFieldGET", FORWARD_ROOT_DIR
				+ "field/ModifyField.jsp");
		successForward.put("DetailedViewFieldGET", FORWARD_ROOT_DIR
				+ "field/ViewField.jsp");
		/* Complex */
		successForward.put("ListComplexGET", FORWARD_ROOT_DIR
				+ "complex/ListComplex.jsp");
		successForward.put("AddComplexGET", FORWARD_ROOT_DIR
				+ "complex/AddComplexForm.jsp");
		successForward.put("AddComplexPOST", FORWARD_ROOT_DIR
				+ "complex/ListComplex.jsp");
		successForward.put("ModifyComplexGET", FORWARD_ROOT_DIR
				+ "complex/ModifyComplex.jsp");
		successForward.put("DetailedViewComplexGET", FORWARD_ROOT_DIR
				+ "complex/ViewComplex.jsp");

		// Bookings
		successForward.put("AddBookingGET", FORWARD_ROOT_DIR
				+ "field/AddBooking.jsp");
		successForward.put("AddBookingPOST", FORWARD_ROOT_DIR
				+ "field/AddBooking.jsp");
		successForward.put("AddManyBookingsGET", FORWARD_ROOT_DIR
				+ "field/AddManyBookings.jsp");
		successForward.put("AddManyBookingsPOST", FORWARD_ROOT_DIR
				+ "field/AddManyBookings.jsp");
		// Errors
		successForward.put("Error404GET", FORWARD_ROOT_DIR + "error/404.jsp");
		successForward.put("Error500GET", FORWARD_ROOT_DIR + "error/500.jsp");
		successForward.put("ErrorExceptionGET", FORWARD_ROOT_DIR
				+ "error/exception.jsp");

		this.successRedirect = new HashMap<String, String>();

		// User
		successRedirect.put("LogoutGET", "/tp-pod/");
		successRedirect.put("LoginPOST", "/tp-pod/user/home");
		successRedirect.put("RegisterPOST", "/tp-pod/");
		successRedirect.put("FinishRegisterGET", "/tp-pod/user/home");
		successRedirect.put("ProfilePOST", "/tp-pod/user/home");

		// ScoreSystem
		successRedirect.put("ModifyScoreSystemPOST",
				"/tp-pod/scoresystem/ShowScoreSystem?modify=true");

		successRedirect.put("ModifyScoreSystemPOST",
				"/tp-pod/ListComplex?modify=true");

		// ExpirationPolicy
		successRedirect.put("ModifyExpirationPolicyPOST",
				"/tp-pod/ListComplex?modifyPolicy=true");
		successRedirect.put("AddExpirationPolicyPOST",
				"/tp-pod/ListComplex?addPolicy=true");
		successRedirect.put("DeleteExpirationPolicyPOST",
				"/tp-pod/ListComplex?deletePolicy=true");

		/* Field */
		successRedirect.put("AddFieldPOST", "/tp-pod/field/list?add=true");
		successRedirect
				.put("DeleteFieldPOST", "/tp-pod/field/list?delete=true");
		successRedirect
				.put("ModifyFieldPOST", "/tp-pod/field/list?modify=true");
		/* Complex */
		successRedirect.put("AddComplexPOST", "/tp-pod/ListComplex?add=true");
		successRedirect.put("DeleteComplexPOST",
				"/tp-pod/ListComplex?delete=true");
		successRedirect.put("ModifyComplexPOST",
				"/tp-pod/ListComplex?modify=true");
	}

	/**
	 * Initializes the failure map
	 */
	private void initializeFailure() {
		this.failureForward = new HashMap<String, String>();

		// User
		failureForward.put("LoginGET", FORWARD_ROOT_DIR
				+ "user/guest/Login.jsp");
		failureForward.put("LoginPOST", FORWARD_ROOT_DIR
				+ "user/guest/Login.jsp");
		failureForward.put("RegisterGET", FORWARD_ROOT_DIR
				+ "user/guest/Register.jsp");
		failureForward.put("RegisterPOST", FORWARD_ROOT_DIR
				+ "user/guest/Register.jsp");
		failureForward.put("ProfilePOST", FORWARD_ROOT_DIR
				+ "user/registered/Profile.jsp");
		failureForward.put("ProfileGET", FORWARD_ROOT_DIR
				+ "user/registered/Profile.jsp");

		// Complex

		failureForward.put("ListComplexGET", FORWARD_ROOT_DIR
				+ "complex/ListComplex.jsp");
		failureForward.put("ListComplexPOST", FORWARD_ROOT_DIR
				+ "complex/ListComplex.jsp");
		failureForward.put("ModifyComplexPOST", FORWARD_ROOT_DIR
				+ "complex/ModifyComplex.jsp");
		failureForward.put("ModifyComplexGET", FORWARD_ROOT_DIR
				+ "complex/ModifyComplex.jsp");

		failureForward.put("AddComplexPOST", FORWARD_ROOT_DIR
				+ "complex/AddComplexForm.jsp");

		failureForward.put("DetailedViewComplexGET", FORWARD_ROOT_DIR
				+ "complex/ViewComplex.jsp");
		// Booking

		failureForward.put("AddBookingGET", FORWARD_ROOT_DIR
				+ "field/AddBooking.jsp");
		failureForward.put("AddBookingPOST", FORWARD_ROOT_DIR
				+ "field/AddBooking.jsp");
		failureForward.put("AddManyBookingsGET", FORWARD_ROOT_DIR
				+ "field/AddManyBookings.jsp");
		failureForward.put("AddManyBookingsPOST", FORWARD_ROOT_DIR
				+ "field/AddManyBookings.jsp");

		// ScoreSystem
		failureForward.put("ModifyScoreSystemGET", FORWARD_ROOT_DIR
				+ "scoresystem/ModifyScoreSystem.jsp");
		failureForward.put("ModifyScoreSystemPOST", FORWARD_ROOT_DIR
				+ "scoresystem/ModifyScoreSystem.jsp");

		// ExpirationPolicy
		failureForward.put("ListExpirationPolicyGET", FORWARD_ROOT_DIR
				+ "complex/ListExpirationPolicy.jsp");
		failureForward.put("AddExpirationPolicyGET", FORWARD_ROOT_DIR
				+ "complex/AddExpirationPolicy.jsp");
		failureForward.put("AddExpirationPolicyPOST", FORWARD_ROOT_DIR
				+ "complex/AddExpirationPolicy.jsp");
		failureForward.put("ModifyExpirationPolicyGET", FORWARD_ROOT_DIR
				+ "complex/ModifyExpirationPolicy.jsp");
		failureForward.put("ModifyExpirationPolicyPOST", FORWARD_ROOT_DIR
				+ "complex/ModifyExpirationPolicy.jsp");

		// Field
		failureForward.put("ListFieldGET", FORWARD_ROOT_DIR
				+ "field/ListField.jsp");
		failureForward.put("AddFieldPOST", FORWARD_ROOT_DIR
				+ "field/AddFieldForm.jsp");
		failureForward.put("AddFieldGET", FORWARD_ROOT_DIR
				+ "field/AddFieldForm.jsp");
		failureForward.put("ModifyFieldGET", FORWARD_ROOT_DIR
				+ "field/ModifyField.jsp");

		failureForward.put("ModifyFieldPOST", FORWARD_ROOT_DIR
				+ "field/ModifyField.jsp");

		failureForward.put(UrlMapper.DEFAULT, FORWARD_ROOT_DIR
				+ "error/500.jsp");

		this.failureRedirect = new HashMap<String, String>();

		// User
		failureRedirect.put("FinishRegisterGET", "/tp-pod/");

		failureRedirect.put("DeleteExpirationPolicyPOST",
				"/tp-pod/ListComplex?delete=false");

		failureRedirect.put("DeleteComplexPOST",
				"/tp-pod/ListComplex?delete=false");
		failureRedirect.put("DeleteFieldPOST",
				"/tp-pod/field/list?delete=false");
		failureRedirect.put(UrlMapper.DEFAULT, "/tp-pod/error/500");

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

	public void redirectSuccess(HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response,
			UrlMapperType type) throws IOException {

		this.redirect(servlet, request, response, type, successRedirect);

	}

	public void redirectFailure(HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response,
			UrlMapperType type) throws IOException {

		this.redirect(servlet, request, response, type, failureRedirect);

	}

	private void forward(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type,
			Map<String, String> map) throws ServletException, IOException {

		String url = this.findUrl(map, servlet, type);

		request.getRequestDispatcher(url).forward(request, response);

	}

	private void redirect(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type,
			Map<String, String> map) throws IOException {

		String url = this.findUrl(map, servlet, type);

		response.sendRedirect(url);

	}

	public void redirectSuccess(HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response,
			UrlMapperType type, Map<String, String> params) throws IOException {

		this
				.redirect(servlet, request, response, type, successRedirect,
						params);

	}

	public void redirectFailure(HttpServlet servlet,
			HttpServletRequest request, HttpServletResponse response,
			UrlMapperType type, Map<String, String> params) throws IOException {

		this
				.redirect(servlet, request, response, type, failureRedirect,
						params);

	}

	private void redirect(HttpServlet servlet, HttpServletRequest request,
			HttpServletResponse response, UrlMapperType type,
			Map<String, String> map, Map<String, String> paramsMap)
			throws IOException {

		String params = this.buildParams(paramsMap);

		String url = this.findUrl(map, servlet, type);

		response.sendRedirect(url + "?" + params);
	}

	private String buildParams(Map<String, String> paramsMap) {

		String params = "";
		boolean isFirst = true;

		for (String key : paramsMap.keySet()) {

			if (isFirst) {
				isFirst = false;
			} else {
				params += "&";
			}

			params += key + "=" + paramsMap.get(key);
		}

		return params;
	}

	public static void sendHome(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.sendRedirect("/tp-pod/user/home");

	}
}
