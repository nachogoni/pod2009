package com.canchita.controller.field;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.service.FieldService;

/**
 * Servlet implementation class DetaildedViewField
 */
public class DetailedViewField extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	Logger logger = Logger.getLogger(DetailedViewField.class.getName());
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("GET request");
		
		FieldService service = new FieldService();

		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			logger.error("Error leyendo id");
			e.printStackTrace();
		}

		try {

			logger.debug("Buscando información de la cancha con id " + id);
			request.setAttribute("field", service.getById(id));
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);

		} catch (Exception e) {
			logger.error("Error buscando información de la cancha con id " + id);
			e.printStackTrace();
		}

	}

}
