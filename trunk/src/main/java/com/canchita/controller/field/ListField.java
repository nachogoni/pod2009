package com.canchita.controller.field;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;

/**
 * Servlet implementation class ListField
 */
public class ListField extends GenericServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListField() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		logger.debug("GET request");
		String search = request.getParameter("search");
		Collection<Field> fields = null;
		int fieldsSize = 0;

		FieldServiceProtocol fieldService = new FieldService();

		ErrorManager errorManager = new ErrorManager();
		if (search == null) {

			try {
				fields = fieldService.listField();
			} catch (PersistenceException e) {
				errorManager.add(e);

				request.setAttribute("searchError", errorManager);

				logger.error("Error en la búsqueda");

				UrlMapper.getInstance().forwardFailure(this, request, response,
						UrlMapperType.GET);

				return;
			}
			fieldsSize = fields.size();

			request.setAttribute("fields", fields);
			request.setAttribute("fieldsLength", fieldsSize);
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);

			return;
		}

		String searchName = request.getParameter("name");
		String searchDescription = request.getParameter("description");
		String searchMaxPrice = request.getParameter("maxPrice");
		String searchNumberOfPlayers = request.getParameter("numberOfPlayers");
		String searchHasRoof = request.getParameter("hasRoof");
		String searchFloorType = request.getParameter("floorType");

		try {

			fields = fieldService.listField(searchName, searchDescription,
					searchMaxPrice, searchNumberOfPlayers, searchHasRoof,
					searchFloorType);
			fieldsSize = fields.size();
		} catch (ValidationException e) {
			logger.debug("Error realizando búsqueda");

			errorManager.add(e);

			request.setAttribute("searchError", errorManager);

			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);

		} catch (Exception e) {
			logger.error("Error realizando búsqueda");
			fields = null;
			fieldsSize = -1;
		}

		request.setAttribute("fields", fields);

		/*
		 * TODO se hizo esto porque no funcionaba el tag fn:length de jstl.
		 * Investigar!
		 */

		request.setAttribute("fieldsLength", fieldsSize);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		return;

	}

}
