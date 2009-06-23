package com.canchita.controller.field;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;
import com.canchita.views.helpers.form.FormHandler;
import com.canchita.views.helpers.form.Pair;

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
		FormHandler formulario = null;
		String search = request.getParameter("search");
		String urlSearch = "";
		Collection<Field> fields = null;
		int fieldsSize = 0;

		FieldServiceProtocol fieldService = new FieldService();

		ErrorManager errorManager = new ErrorManager();
		if (search == null) {
			logger.debug("Entra por primera vez");

			/* Form is sent to the view */
			formulario = new SearchFieldForm();
			request.setAttribute("formulario", formulario);
			logger.debug("Se crea el formulario correctamente");

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
			logger.debug("Se pudo generar el listado");
			fieldsSize = fields.size();

		} else {
			logger.debug("Entra y el formulario no está vacío");

			/* Get Form */
			formulario = new SearchFieldForm();

			/* Load form with request values */
			formulario.loadValues(request);
			logger
					.debug("Se carga el formulario con la información del request");

			if (!formulario.isValid()) {
				logger.debug("Formulario inválido");
				request.setAttribute("formulario", formulario);
				errorManager.add("Error en el formulario");

				try {
					fields = fieldService.listField();
					logger.debug("Se pudo generar el listado");
					fieldsSize = fields.size();
				} catch (Exception e1) {
					logger.debug("Error al listar canchas " + e1.getMessage());
				}

			} else {

				request.setAttribute("formulario", formulario);
				logger.debug("El formulario es válido");

				String searchName = request.getParameter("name");
				String searchDescription = request.getParameter("description");
				String searchMaxPrice = request.getParameter("maxPrice");
				String searchNumberOfPlayers = request
						.getParameter("numberOfPlayers");
				String searchHasRoof = request.getParameter("hasRoof");
				String searchFloorType = request.getParameter("floorType");
				String searchAddress = request.getParameter("address");
				String searchNeighbourhood = request
						.getParameter("neighbourhood");
				String searchTown = request.getParameter("town");
				String searchState = request.getParameter("state");
				String searchCountry = request.getParameter("country");

				String searchDay = request.getParameter("day");
				String searchFrom = request.getParameter("from");
				String searchTo = request.getParameter("to");
				
				urlSearch = String
						.format(
								"name=%s&description=%s&maxPrice=%s&numberOfPlayers="
										+ "%s&hasRoof=%s&floorType=%s&neighbourhood=%s&"
										+ "town=%s&state=%s&country=%s&address=%s&" +
												"day=%s&from=%s&to=%s&",
								searchName, searchDescription, searchMaxPrice,
								searchNumberOfPlayers, searchHasRoof,
								searchFloorType, searchNeighbourhood,
								searchTown, searchState, searchCountry,
								searchAddress , searchDay, searchFrom, searchTo);

				DateTimeFormatter parser = DateTimeFormat.forPattern("dd/MM/yyyy");
				DateTime date = null,startTime = null,endTime = null;
				
				if( ! searchDay.isEmpty() ) {
					date = parser.parseDateTime(searchDay);
					startTime = this.getDate(date, searchFrom);
					endTime = this.getDate(date, searchTo);
				}

				
				logger.debug("urlSearch = " + urlSearch);
				try {
					fields = fieldService.listField(searchName,
							searchDescription, searchMaxPrice,
							searchNumberOfPlayers, searchHasRoof,
							searchFloorType, searchNeighbourhood, searchTown,
							searchState, searchCountry, searchAddress,startTime,endTime);
					fieldsSize = fields.size();
				} catch (ValidationException e) {
					logger
							.debug("Error realizando búsqueda: "
									+ e.getMessage());

					errorManager.add(e);

					request.setAttribute("searchError", errorManager);

					UrlMapper.getInstance().forwardFailure(this, request,
							response, UrlMapperType.GET);
					return;

				} catch (Exception e) {
					logger.error("Error realizando búsqueda:" + e.getMessage());
					fields = null;
					fieldsSize = -1;

					UrlMapper.getInstance().forwardFailure(this, request,
							response, UrlMapperType.GET);
					return;
				}
			}
		}
		// ordeno la lista
		List<Field> list = new ArrayList<Field>(fields);
		String typesort = "";

		ArrayList<String> parametersSort = new ArrayList<String>();
		HashMap<String, Pair<Comparator<Field>, Comparator<Field>>> comparators = new HashMap<String, Pair<Comparator<Field>, Comparator<Field>>>();
		parametersSort.add("sortName");
		comparators.put("sortName", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareNames(true),Field.compareNames(false)));
		parametersSort.add("sortComplex");
		comparators.put("sortComplex", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareComplex(true),Field.compareComplex(false)));
		parametersSort.add("sortDescription");
		comparators.put("sortDescription", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareDescription(true),Field.compareDescription(false)));
		parametersSort.add("sortPlayers");
		comparators.put("sortPlayers", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareNumberOfPlayers(true),Field.compareNumberOfPlayers(false)));
		parametersSort.add("sortRoof");
		comparators.put("sortRoof", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareRoof(true),Field.compareRoof(false)));
		parametersSort.add("sortFloor");
		comparators.put("sortFloor", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareFloorType(true),Field.compareFloorType(false)));
		parametersSort.add("sortPrice");
		comparators.put("sortPrice", new Pair<Comparator<Field>, Comparator<Field>>(Field.comparePrice(true),Field.comparePrice(false)));
		parametersSort.add("sortMaintenance");
		comparators.put("sortMaintenance", new Pair<Comparator<Field>, Comparator<Field>>(Field.compareMaintenance(true),Field.compareMaintenance(false)));

		for (String e : parametersSort) {
			if ((typesort = request.getParameter(e)) != null) {
				Collections.sort(list, typesort.equals("ASC") ? comparators.get(e).getFirst() : comparators.get(e).getSecond());
				request.setAttribute(String.format("%sTypeR", e), typesort.equals("ASC") ? "DESC" : "ASC");
				request.setAttribute(String.format("%sType", e), typesort);
			} else {
				request.setAttribute(String.format("%sTypeR", e), "ASC");
			}
		}

		request.setAttribute("fields", list);
		request.setAttribute("urlParameters", urlSearch);
		request.setAttribute("fieldsLength", fieldsSize);

		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);

		return;

	}

	private DateTime getDate(DateTime date, String offset) {

		String[] hoursAndMins = offset.split(":");

		return date.withTime(Integer.parseInt(hoursAndMins[0]), Integer
				.parseInt(hoursAndMins[1]), 0, 0);

	}
	
}
