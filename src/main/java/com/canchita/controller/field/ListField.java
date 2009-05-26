package com.canchita.controller.field;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.exception.ValidationException;
import com.canchita.model.field.Field;
import com.canchita.service.FieldService;
import com.canchita.service.FieldServiceProtocol;

/**
 * Servlet implementation class ListField
 */
public class ListField extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListField() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String search = request.getParameter("search");
		Collection<Field> fields = null;
		int fieldsSize = 0;
		
		FieldServiceProtocol fieldService = new FieldService();
		
		if( search == null ) {
			
			fields = fieldService.listField();
			
			request.setAttribute("fields", fields);
			
			UrlMapper.getInstance().forwardSuccess(this, request, response,
					UrlMapperType.GET);
			
			return;
		}

		try {
			
			fields = fieldService.listField(search);
			fieldsSize = fields.size();
		}
		catch(ValidationException e) {
			ErrorManager errorManager = new ErrorManager();
			
			errorManager.add(e);
			
			request.setAttribute("searchError", errorManager);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			
			return;			
			
		}
		catch(Exception e) {
			fields = null;
			fieldsSize = -1;
		}

		request.setAttribute("fields", fields);
		
		/*
		 * TODO se hizo esto porque no funcionaba el tag
		 * fn:length de jstl. Investigar!
		 */
		
		request.setAttribute("fieldsLength", fieldsSize);
		
		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
		
		return;
		
	}

}
