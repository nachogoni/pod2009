package com.canchita.controller.field;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.canchita.controller.GenericServlet;
import com.canchita.controller.complex.AddComplex;
import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.service.ComplexService;
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
        // TODO Auto-generated constructor stub
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
			logger.error("Error leyendo id");
		}

			logger.debug("Eliminando cancha con id " + id);
			try {
				delService.deleteField(id);
			} catch (ElementNotExistsException e) {
				
				UrlMapper.getInstance().redirectFailure(this, request, response,
						UrlMapperType.POST);
				
			}

		
		UrlMapper.getInstance().redirectSuccess(this, request, response,
				UrlMapperType.POST);

	}

}