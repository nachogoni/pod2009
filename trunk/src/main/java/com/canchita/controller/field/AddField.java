package com.canchita.controller.field;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Expiration;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.FloorType;
import com.canchita.service.FieldService;
import com.canchita.views.helpers.FormHandler;

/**
 * Servlet implementation class AddField
 */
public class AddField extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AddField.class.getName());
	private FormHandler formulario;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddField() {
		super();
		formulario = new FormField();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("GET request");

		/*Get Form*/
		formulario = new FormField();

		/* Form is sent to the view*/
		request.setAttribute("formulario", this.formulario);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		logger.debug("Saliendo del controlador");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("POST request");
	}
}
