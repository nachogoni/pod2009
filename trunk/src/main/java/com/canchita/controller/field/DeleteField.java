package com.canchita.controller.field;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.FieldException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.service.FieldService;

/**
 * Servlet implementation class DeleteField
 */
public class DeleteField extends GenericServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteField() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		logger.debug("POST request");
		FieldService delService = new FieldService();

		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error leyendo id de cancha");
		}

			logger.debug("Eliminando cancha con id " + id);
			try {
				delService.deleteField(id);
			} catch (PersistenceException e) {

				Map<String, String> map = new HashMap<String, String>();

				map.put("delete", "false");
				
				UrlMapper.getInstance().redirectFailure(this, request, response,
						UrlMapperType.POST,map);
				return;
				
			} catch (FieldException e) {

				Map<String, String> map = new HashMap<String, String>();

				map.put("hasBookings", "true");
				
				UrlMapper.getInstance().redirectFailure(this, request, response,
						UrlMapperType.POST,map);
				return;
			}

			Map<String, String> map = new HashMap<String, String>();

			map.put("delete", "true");
		
			UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST,map);

	}

}
