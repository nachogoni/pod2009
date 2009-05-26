package com.canchita.controller.field;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.canchita.controller.helper.ErrorManager;
import com.canchita.controller.helper.UrlMapper;
import com.canchita.controller.helper.UrlMapperType;
import com.canchita.model.booking.Expiration;
import com.canchita.model.exception.ElementNotExistsException;
import com.canchita.model.exception.PersistenceException;
import com.canchita.model.field.FloorType;
import com.canchita.service.FieldService;

/**
 * Servlet implementation class ModifyField
 */
public class ModifyField extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyField() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		FieldService service = new FieldService();

		Long id = null;

		try {
			id = Long.parseLong((request.getParameter("id")));
		} catch (NumberFormatException e) {
			ErrorManager error = new ErrorManager();
			
			error.add(e);
			
			request.setAttribute("error", error);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			return;
		}

		try {
			request.setAttribute("field", service.getById(id));
		} catch (ElementNotExistsException e) {
			ErrorManager error = new ErrorManager();
			
			error.add(e);
			
			request.setAttribute("error", error);
			
			UrlMapper.getInstance().forwardFailure(this, request, response,
					UrlMapperType.GET);
			return;
		}


		UrlMapper.getInstance().forwardSuccess(this, request, response,
				UrlMapperType.GET);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FieldService modifyService = new FieldService();
		
		Long id = -1L;
		Long idComplex = -1L;
		Boolean hasRoof = false;
		FloorType floor = FloorType.CONCRETE;
		Expiration expiration = null;
		
		ErrorManager error = new ErrorManager();

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		try{
			idComplex = Long.getLong(request.getParameter("idComplex"));
		} catch (NumberFormatException nfe) {
			error.add("Complejo invalido");
		}
		
		try{
			hasRoof = Boolean.valueOf(request.getParameter("hasRoof"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de techo invalida");
		}
		
		try{
			floor = FloorType.valueOf(request.getParameter("floor"));
		} catch (Exception nfe) {
			error.add("Informacion sobre el tipo de piso invalida");
		}

		try{
			id = Long.parseLong(request.getParameter("id"));
		} catch (NumberFormatException nfe) {
			error.add("Valores en la cancha incorrectos");
		}
		
		//expiration = request.getParameter("expiration"); TODO:

		if (name == null) {
			error.add("Falta el nombre de la Cancha");
		}	
		/*if(idComplex == null){
			error.add("Falta el complejo");
		}
		if(hasRoof == null){
			error.add("Falta si la cancha tiene techo");
		}
		if(floor == null){
			error.add("Falta el tipo de piso de la cancha");
		}		
		if(expiration == null){
			error.add("Falta el tiempo de expiracion");
		}*/	
		
		if (error.size() != 0) {
			request.setAttribute("error", error);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		try {
			modifyService.updateField(id, name, description, idComplex, hasRoof, floor);
		} catch (ElementNotExistsException e) {
			error.add(e);
			request.setAttribute("error", error);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}catch (PersistenceException e) {
			error.add(e);
			request.setAttribute("error", error);
			UrlMapper.getInstance().forwardFailure(this, request, response, UrlMapperType.POST);
			return;
		}

		UrlMapper.getInstance().redirectSuccess(this, request, response, UrlMapperType.POST);
		
	}

}
